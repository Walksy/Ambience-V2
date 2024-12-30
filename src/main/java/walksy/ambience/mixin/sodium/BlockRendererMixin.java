package walksy.ambience.mixin.sodium;

import net.caffeinemc.mods.sodium.api.util.ColorARGB;
import net.caffeinemc.mods.sodium.client.model.quad.properties.ModelQuadFacing;
import net.caffeinemc.mods.sodium.client.model.quad.properties.ModelQuadOrientation;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.ChunkBuildBuffers;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.buffers.ChunkModelBuilder;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.DefaultTerrainRenderPasses;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.TerrainRenderPass;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.DefaultMaterials;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.Material;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.parameters.AlphaCutoffParameter;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.parameters.MaterialParameters;
import net.caffeinemc.mods.sodium.client.render.chunk.translucent_sorting.TranslucentGeometryCollector;
import net.caffeinemc.mods.sodium.client.render.chunk.vertex.builder.ChunkMeshBufferBuilder;
import net.caffeinemc.mods.sodium.client.render.chunk.vertex.format.ChunkVertexEncoder;
import net.caffeinemc.mods.sodium.client.render.frapi.mesh.MutableQuadViewImpl;
import net.caffeinemc.mods.sodium.client.render.frapi.render.AbstractBlockRenderContext;
import net.caffeinemc.mods.sodium.client.render.texture.SpriteFinderCache;
import net.minecraft.client.texture.Sprite;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import walksy.ambience.manager.EnvironmentColorOverrider;
import walksy.ambience.manager.NativeImageManager;

@Mixin(targets = "net.caffeinemc.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderer", remap = false)
public abstract class BlockRendererMixin extends AbstractBlockRenderContext {

    @Shadow @Final private ChunkVertexEncoder.Vertex[] vertices;

    @Shadow @Final private Vector3f posOffset;

    @Shadow @Nullable protected abstract TerrainRenderPass attemptPassDowngrade(Sprite sprite, TerrainRenderPass pass);

    @Shadow private TranslucentGeometryCollector collector;

    @Shadow private ChunkBuildBuffers buffers;


    /**
     * Overriding sodium's BlockRenderer buffer quad to override the original sprite and apply our config's color
     */

    @Inject(method = "bufferQuad", at = @At("HEAD"), cancellable = true)
    public void overrideQuadBuffers(MutableQuadViewImpl quad, float[] brightnesses, Material material, CallbackInfo ci)
    {
        ModelQuadOrientation orientation = ModelQuadOrientation.NORMAL;
        ChunkVertexEncoder.Vertex[] vertices = this.vertices;
        Vector3f offset = this.posOffset;

        int materialBits;
        int color = EnvironmentColorOverrider.BlockOverrider.getBlockColor(this.state.getBlock());
        for(int dstIndex = 0; dstIndex < 4; ++dstIndex) {
            materialBits = orientation.getVertexIndex(dstIndex);
            ChunkVertexEncoder.Vertex out = vertices[dstIndex];
            out.x = quad.x(materialBits) + offset.x;
            out.y = quad.y(materialBits) + offset.y;
            out.z = quad.z(materialBits) + offset.z;
            out.color = ColorARGB.toABGR(color == -1 ? quad.color(materialBits) : color);
            out.ao = brightnesses[materialBits];
            out.u = quad.u(materialBits);
            out.v = quad.v(materialBits);
            out.light = quad.lightmap(materialBits);
        }

        Sprite atlasSprite = quad.sprite(SpriteFinderCache.forBlockAtlas());
        if (color != -1)
        {
            NativeImageManager.applyGrayscale(atlasSprite);
        }
        materialBits = material.bits();
        ModelQuadFacing normalFace = quad.normalFace();
        TerrainRenderPass pass = material.pass;
        TerrainRenderPass downgradedPass = this.attemptPassDowngrade(atlasSprite, pass);
        if (downgradedPass != null) {
            pass = downgradedPass;
        }

        if (pass.isTranslucent() && this.collector != null) {
            this.collector.appendQuad(quad.getFaceNormal(), vertices, normalFace);
        }

        if (downgradedPass != null && material == DefaultMaterials.TRANSLUCENT && pass == DefaultTerrainRenderPasses.CUTOUT) {
            materialBits = MaterialParameters.pack(AlphaCutoffParameter.ONE_TENTH, material.mipped);
        }

        ChunkModelBuilder builder = this.buffers.get(pass);
        ChunkMeshBufferBuilder vertexBuffer = builder.getVertexBuffer(normalFace);
        vertexBuffer.push(vertices, materialBits);
        builder.addSprite(atlasSprite);
        ci.cancel();
    }
}
