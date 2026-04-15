/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package com.teamtea.eclipticseasons.client.util;

import com.mojang.blaze3d.platform.Transparency;
import java.util.Arrays;
import net.minecraft.client.model.geom.builders.UVPair;
import net.minecraft.client.renderer.FaceInfo;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.cuboid.FaceBakery;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.util.ARGB;
import org.jetbrains.annotations.Contract;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;


public class MutableQuad {
    private final Vector3f[] positions = new Vector3f[] {
            new Vector3f(),
            new Vector3f(),
            new Vector3f(),
            new Vector3f()
    };
    private final long[] uvs = new long[4];
    private final int[] normals = new int[4];
    private final int[] colors = new int[4];

    private Direction direction = Direction.DOWN;
    @Nullable
    private TextureAtlasSprite sprite;
    @Nullable
    private ChunkSectionLayer chunkLayer;
    @Nullable
    private RenderType itemRenderType;
    private int tintIndex = -1;
    private boolean shade = true;
    private int lightEmission;
    private boolean ambientOcclusion;
    /**
     * This is only used to reuse position vectors when possible.
     */
    @Nullable
    private BakedQuad lastSourceQuad;

    public MutableQuad() {
        reset();
    }

    /**
     * {@return the x-component of a vertex's position}
     */
    @Contract(pure = true)
    public float x(int vertexIndex) {
        return positions[vertexIndex].x;
    }

    /**
     * {@return the y-component of a vertex's position}
     */
    @Contract(pure = true)
    public float y(int vertexIndex) {
        return positions[vertexIndex].y;
    }

    /**
     * {@return the z-component of a vertex's position}
     */
    @Contract(pure = true)
    public float z(int vertexIndex) {
        return positions[vertexIndex].z;
    }

    /**
     * {@return a component of a vertex's position}
     *
     * @see Vector3f#get(int)
     */
    @Contract(pure = true)
    public float positionComponent(int vertexIndex, int componentIndex) {
        return positions[vertexIndex].get(componentIndex);
    }

    /**
     * Copies a vertex's position into a new vector and returns it.
     */
    @Contract(pure = true)
    public Vector3f copyPosition(int vertexIndex) {
        return new Vector3f(positions[vertexIndex]);
    }

    /**
     * Copies a vertex's position into the given vector and returns it.
     */
    public Vector3f copyPosition(int vertexIndex, Vector3f dest) {
        var pos = positions[vertexIndex];
        dest.set(pos);
        return dest;
    }

    /**
     * Sets the x-component of a vertex's position.
     */
    public MutableQuad setX(int vertexIndex, float x) {
        positions[vertexIndex].x = x;
        return this;
    }

    /**
     * Sets the y-component of a vertex's position.
     */
    public MutableQuad setY(int vertexIndex, float y) {
        positions[vertexIndex].y = y;
        return this;
    }

    /**
     * Sets the x-component of a vertex's position.
     */
    public MutableQuad setZ(int vertexIndex, float z) {
        positions[vertexIndex].z = z;
        return this;
    }

    /**
     * Sets a component of a vertex's position.
     *
     * @see Vector3f#setComponent(int, float)
     */
    public MutableQuad setPositionComponent(int vertexIndex, int componentIndex, float value) {
        positions[vertexIndex].setComponent(componentIndex, value);
        return this;
    }

    /**
     * Sets a vertex's position.
     */
    public MutableQuad setPosition(int vertexIndex, float x, float y, float z) {
        positions[vertexIndex].set(x, y, z);
        return this;
    }

    /**
     * Sets a vertex's position.
     */
    public MutableQuad setPosition(int vertexIndex, Vector3fc position) {
        positions[vertexIndex].set(position);
        return this;
    }

    /**
     * Sets the positions of this quad to form a rectangle on the given block side using a coordinate-system matching
     * the default orientation of sprites in Vanilla block-models.
     * <p>
     * Inspired by the Fabric Renderer API method {@code square}.
     * <p>
     * The left, bottom, right and top parameters correspond to the default sprite orientation in Vanilla block models.
     * For {@link Direction#UP} the "up" direction is facing {@link Direction#NORTH}, while for {@link Direction#DOWN},
     * it faces {@link Direction#SOUTH}.
     * <p>All coordinates use a normalized [0,1] range.
     * <p>Passing left=0, bottom=0, right=1, top=1, depth=0 will produce a face on the blocks {@code side}.
     */
    public MutableQuad setCubeFaceFromSpriteCoords(Direction side,
                                                   float left,
                                                   float bottom,
                                                   float right,
                                                   float top,
                                                   float depth) {
        this.direction = side;

        switch (side) {
            case NORTH -> {
                // -Z (looking south at north face)
                // left is +X, bottom is -Y
                positions[0].set(1 - left, top, depth);
                positions[1].set(1 - left, bottom, depth);
                positions[2].set(1 - right, bottom, depth);
                positions[3].set(1 - right, top, depth);
            }
            case SOUTH -> {
                // +Z (looking north at south face)
                // left is +X, bottom is -Y
                positions[0].set(left, top, 1 - depth);
                positions[1].set(left, bottom, 1 - depth);
                positions[2].set(right, bottom, 1 - depth);
                positions[3].set(right, top, 1 - depth);
            }
            case EAST -> {
                // -X (looking west at east face)
                // left is +Z, bottom is -Y
                positions[0].set(1 - depth, top, 1 - left);
                positions[1].set(1 - depth, bottom, 1 - left);
                positions[2].set(1 - depth, bottom, 1 - right);
                positions[3].set(1 - depth, top, 1 - right);
            }
            case WEST -> {
                // +X (looking east at west face)
                // left is -Z, bottom is -Y
                positions[0].set(depth, top, left);
                positions[1].set(depth, bottom, left);
                positions[2].set(depth, bottom, right);
                positions[3].set(depth, top, right);
            }
            case UP -> {
                // -Y (looking down at up face)
                // left is -X, bottom is +Z
                positions[0].set(left, 1 - depth, 1 - top);
                positions[1].set(left, 1 - depth, 1 - bottom);
                positions[2].set(right, 1 - depth, 1 - bottom);
                positions[3].set(right, 1 - depth, 1 - top);
            }
            case DOWN -> {
                // +Y (looking up at down face)
                // left is -X, bottom is -Z
                positions[0].set(left, depth, top);
                positions[1].set(left, depth, bottom);
                positions[2].set(right, depth, bottom);
                positions[3].set(right, depth, top);
            }
        }
        return this;
    }

    /**
     * Same as {@link #setCubeFace(Direction, float, float, float, float, float, float)}, but uses the full cube.
     */
    public MutableQuad setFullCubeFace(Direction side) {
        return setCubeFace(side, 0, 0, 0, 1, 1, 1);
    }

    /**
     * Same as {@link #setCubeFace(Direction, float, float, float, float, float, float)}, but takes the from and to
     * positions from vectors.
     */
    public MutableQuad setCubeFace(Direction side, Vector3fc from, Vector3fc to) {
        return setCubeFace(side, from.x(), from.y(), from.z(), to.x(), to.y(), to.z());
    }

    /**
     * Sets the positions of this quad to the face of a cube as it would be defined in a Vanilla block model.
     * <p>All coordinates use a normalized [0,1] range.
     */
    public MutableQuad setCubeFace(Direction side,
                                   float fromX,
                                   float fromY,
                                   float fromZ,
                                   float toX,
                                   float toY,
                                   float toZ) {
        this.direction = side;

        for (int i = 0; i < 4; i++) {
            var vertexInfo = FaceInfo.fromFacing(side).getVertexInfo(i);
            positions[i].set(
                    vertexInfo.xFace().select(fromX, fromY, fromZ, toX, toY, toZ),
                    vertexInfo.yFace().select(fromX, fromY, fromZ, toX, toY, toZ),
                    vertexInfo.zFace().select(fromX, fromY, fromZ, toX, toY, toZ));
        }
        return this;
    }

    /**
     * {@return the horizontal texture coordinate in atlas-space for a vertex}
     */
    @Contract(pure = true)
    public float u(int vertexIndex) {
        return UVPair.unpackU(uvs[vertexIndex]);
    }

    /**
     * {@return the vertical texture coordinate in atlas-space for a vertex}
     */
    @Contract(pure = true)
    public float v(int vertexIndex) {
        return UVPair.unpackV(uvs[vertexIndex]);
    }

    /**
     * {@return the a texture coordinate in atlas-space for a vertex}
     */
    @Contract(pure = true)
    public float uvComponent(int vertexIndex, int componentIndex) {
        return switch (componentIndex) {
            case 0 -> u(vertexIndex);
            case 1 -> v(vertexIndex);
            default -> throw new IllegalArgumentException("Invalid UV index: " + componentIndex);
        };
    }

    /**
     * {@return the texture coordinates in atlas-space for a vertex in packed form}
     *
     * @see UVPair#unpackU(long)
     * @see UVPair#unpackV(long)
     */
    @Contract(pure = true)
    public long packedUv(int vertexIndex) {
        return uvs[vertexIndex];
    }

    /**
     * Same as {@link #copyUv(int, Vector2f)}, but constructs a destination vector automatically.
     */
    @Contract(pure = true)
    public Vector2f copyUv(int vertexIndex) {
        return copyUv(vertexIndex, new Vector2f());
    }

    /**
     * Copies the texture coordinates of a vertex into a given vector and returns it.
     */
    public Vector2f copyUv(int vertexIndex, Vector2f dest) {
        var packedUv = uvs[vertexIndex];
        dest.x = UVPair.unpackU(packedUv);
        dest.y = UVPair.unpackV(packedUv);
        return dest;
    }

    /**
     * Sets the texture coordinate of a vertex.
     *
     * <p>Note that this method expects texture coordinates in the coordinate space of the atlas, not the sprite.
     *
     * @see #setUvFromSprite(int, float, float)
     */
    public MutableQuad setUv(int vertexIndex, float u, float v) {
        uvs[vertexIndex] = UVPair.pack(u, v);
        return this;
    }

    /**
     * Sets the texture coordinate of a vertex.
     *
     * <p>Note that this method expects texture coordinates in the coordinate space of the atlas, not the sprite.
     *
     * @see #setUvFromSprite(int, Vector2fc)
     */
    public MutableQuad setUv(int vertexIndex, Vector2fc uv) {
        return setUv(vertexIndex, uv.x(), uv.y());
    }

    /**
     * Sets a component of the texture coordinate of a vertex.
     *
     * <p>Note that this method expects texture coordinates in the coordinate space of the atlas, not the sprite.
     *
     * @see #setUvFromSprite(int, float, float)
     */
    public MutableQuad setUvComponent(int vertexIndex, int componentIndex, float value) {
        return switch (componentIndex) {
            case 0 -> setUv(vertexIndex, value, v(vertexIndex));
            case 1 -> setUv(vertexIndex, u(vertexIndex), value);
            default -> throw new IllegalArgumentException("Invalid UV index: " + componentIndex);
        };
    }

    /**
     * Sets the texture coordinate of a vertex from their packed representation.
     *
     * <p>Note that this method expects texture coordinates in the coordinate space of the atlas, not the sprite.
     *
     * @see UVPair
     */
    public MutableQuad setPackedUv(int vertexIndex, long packedUv) {
        uvs[vertexIndex] = packedUv;
        return this;
    }

    /**
     * Assigns UV coordinates to a vertex of the current quad based on its {@linkplain #sprite() sprite} and the
     * given UV coordinates within that sprite.
     */
    public MutableQuad setUvFromSprite(int vertexIndex, float u, float v) {
        TextureAtlasSprite sprite = requiredSprite();
        return setUv(vertexIndex, sprite.getU(u), sprite.getV(v));
    }

    /**
     * Assigns UV coordinates to a vertex of the current quad based on its {@linkplain #sprite() sprite} and the
     * given UV coordinates within that sprite.
     */
    public MutableQuad setUvFromSprite(int vertexIndex, Vector2fc uv) {
        return setUvFromSprite(vertexIndex, uv.x(), uv.y());
    }

    @Contract(pure = true)
    public int tintIndex() {
        return tintIndex;
    }

    public MutableQuad setTintIndex(int tintIndex) {
        this.tintIndex = tintIndex;
        return this;
    }

    @Contract(pure = true)
    public Direction direction() {
        return direction;
    }

    public MutableQuad setDirection(Direction direction) {
        this.direction = direction;
        return this;
    }

    /**
     * {@return the sprite associated with the quad or null if no sprite has been set yet}
     *
     * <p>Note that {@link BakedQuad} must have an associated sprite.
     */
    @Contract(pure = true)
    @Nullable
    public TextureAtlasSprite sprite() {
        return sprite;
    }

    /**
     * Same as {@link #sprite()}, but throws an exception if no sprite is set on the quad yet.
     *
     * @throws IllegalStateException If no sprite is set yet.
     */
    @Contract(pure = true)
    public TextureAtlasSprite requiredSprite() {
        if (sprite == null) {
            throw new IllegalStateException("A sprite has to be set on this quad before UVs are manipulated");
        }
        return sprite;
    }

    /// {@return the chunk layer associated with the quad or null if no chunk layer has been set yet}
    ///
    /// Note that [BakedQuad] must have an associated chunk layer
    @Contract(pure = true)
    @Nullable
    public ChunkSectionLayer chunkLayer() {
        return chunkLayer;
    }

    /// Same as [#chunkLayer()], but throws an exception if no chunk layer is set on the quad yet.
    ///
    /// @throws IllegalStateException If no chunk layer is set yet
    @Contract(pure = true)
    public ChunkSectionLayer requiredChunkLayer() {
        if (chunkLayer == null) {
            throw new IllegalStateException("A ChunkSectionLayer has to be set on this quad before baking");
        }
        return chunkLayer;
    }

    /// {@return the item render type associated with the quad or null if no item render type has been set yet}
    ///
    /// Note that [BakedQuad] must have an associated item render type
    @Contract(pure = true)
    @Nullable
    public RenderType itemRenderType() {
        return itemRenderType;
    }

    /// Same as [#itemRenderType()], but throws an exception if no item render type is set on the quad yet.
    ///
    /// @throws IllegalStateException If no item render type is set yet
    @Contract(pure = true)
    public RenderType requiredItemRenderType() {
        if (itemRenderType == null) {
            throw new IllegalStateException("An item RenderType has to be set on this quad before baking");
        }
        return itemRenderType;
    }


    public MutableQuad setSprite(Material.Baked material, Transparency transparency) {
        RenderType itemRenderType;
        if (material.sprite().atlasLocation().equals(TextureAtlas.LOCATION_BLOCKS)) {
            itemRenderType = transparency.hasTranslucent() ? Sheets.translucentBlockItemSheet() : Sheets.cutoutBlockItemSheet();
        } else {
            itemRenderType = transparency.hasTranslucent() ? Sheets.translucentItemSheet() : Sheets.cutoutItemSheet();
        }
        setSprite(material.sprite(), ChunkSectionLayer.byTransparency(transparency), itemRenderType);
        return this;
    }


    public MutableQuad setSprite(TextureAtlasSprite sprite, ChunkSectionLayer chunkLayer, RenderType itemRenderType) {
        this.sprite = sprite;
        this.chunkLayer = chunkLayer;
        this.itemRenderType = itemRenderType;
        return this;
    }

    /**
     * Changes the sprite and remaps the UV to the new sprites position in the texture atlas.
     *
     * @throws IllegalStateException If no sprite is currently set. There would be nothing to remap from.
     */
    public MutableQuad setSpriteAndMoveUv(Material.Baked material, Transparency transparency) {
        transformUvsFromAtlasToSprite();
        setSprite(material, transparency);
        transformUvsFromSpriteToAtlas();
        return this;
    }

    /**
     * Changes the sprite and remaps the UV to the new sprites position in the texture atlas.
     *
     * @throws IllegalStateException If no sprite is currently set. There would be nothing to remap from.
     */
    public MutableQuad setSpriteAndMoveUv(TextureAtlasSprite sprite, ChunkSectionLayer chunkLayer, RenderType itemRenderType) {
        transformUvsFromAtlasToSprite();
        setSprite(sprite, chunkLayer, itemRenderType);
        transformUvsFromSpriteToAtlas();
        return this;
    }

    @Contract(pure = true)
    public boolean shade() {
        return shade;
    }

    public MutableQuad setShade(boolean shade) {
        this.shade = shade;
        return this;
    }

    @Contract(pure = true)
    public int lightEmission() {
        return lightEmission;
    }

    public MutableQuad setLightEmission(int lightEmission) {
        this.lightEmission = lightEmission;
        return this;
    }


    /**
     * {@return the color of a given vertex in ARGB form}
     *
     * @see ARGB
     */
    @Contract(pure = true)
    public int color(int vertexIndex) {
        return colors[vertexIndex];
    }

    /**
     * Sets the color of all vertices to a packed ARGB color.
     *
     * @see ARGB
     */
    public MutableQuad setColor(int packedColor) {
        colors[0] = packedColor;
        colors[1] = packedColor;
        colors[2] = packedColor;
        colors[3] = packedColor;
        return this;
    }

    /**
     * Sets the color of a vertex to a packed ARGB color.
     *
     * @see ARGB
     */
    public MutableQuad setColor(int vertexIndex, int packedColor) {
        colors[vertexIndex] = packedColor;
        return this;
    }

    /**
     * Sets the color of a vertex from integer components (0-255).
     *
     * @see ARGB
     */
    public MutableQuad setColor(int vertexIndex, int r, int g, int b, int a) {
        return setColor(vertexIndex, ARGB.color(a, r, g, b));
    }



    public MutableQuad setFrom(BakedQuad quad) {
        lastSourceQuad = quad;
        for (int i = 0; i < 4; i++) {
            positions[i].set(quad.position(i));
            uvs[i] = quad.packedUV(i);
        }
        direction = quad.direction();
        BakedQuad.MaterialInfo materialInfo = quad.materialInfo();
        sprite = materialInfo.sprite();
        chunkLayer = materialInfo.layer();
        itemRenderType = materialInfo.itemRenderType();
        tintIndex = materialInfo.tintIndex();
        shade = materialInfo.shade();
        lightEmission = materialInfo.lightEmission();
        return this;
    }

    /**
     * Assumes that the UV coordinates are in sprite-space and transforms
     * them to atlas-space.
     */
    private void transformUvsFromSpriteToAtlas() {
        TextureAtlasSprite sprite = requiredSprite();
        for (int i = 0; i < 4; i++) {
            long packedUv = packedUv(i);
            setUv(i, sprite.getU(UVPair.unpackU(packedUv)), sprite.getV(UVPair.unpackV(packedUv)));
        }
    }

    /**
     * Assumes that the UV coordinates are in atlas-space and transforms
     * them to sprite-space.
     */
    private void transformUvsFromAtlasToSprite() {
        TextureAtlasSprite sprite = requiredSprite();
        float uOrigin = sprite.getU0();
        float vOrigin = sprite.getV0();
        float uWidth = sprite.getU1() - uOrigin;
        float vWidth = sprite.getV1() - vOrigin;

        for (int i = 0; i < 4; i++) {
            long packedUv = packedUv(i);
            float u = (UVPair.unpackU(packedUv) - uOrigin) / uWidth;
            float v = (UVPair.unpackV(packedUv) - vOrigin) / vWidth;
            setUv(i, u, v);
        }
    }

    @Contract(pure = true)
    public BakedQuad toBakedQuad() {
        TextureAtlasSprite sprite = requiredSprite();
        ChunkSectionLayer chunkLayer = requiredChunkLayer();
        RenderType itemRenderType = requiredItemRenderType();

        // Try to reuse objects from the last baked quad that we copied from to reduce allocations if
        // the quad was only partially transformed.
        Vector3fc pos0;
        Vector3fc pos1;
        Vector3fc pos2;
        Vector3fc pos3;
        BakedQuad.MaterialInfo materialInfo;
        if (lastSourceQuad != null) {
            pos0 = reuseVector(lastSourceQuad, positions[0]);
            pos1 = reuseVector(lastSourceQuad, positions[1]);
            pos2 = reuseVector(lastSourceQuad, positions[2]);
            pos3 = reuseVector(lastSourceQuad, positions[3]);

            BakedQuad.MaterialInfo srcInfo = lastSourceQuad.materialInfo();
            boolean canReuseMaterialInfo = sprite == srcInfo.sprite() &&
                    chunkLayer == srcInfo.layer() &&
                    itemRenderType == srcInfo.itemRenderType() &&
                    tintIndex == srcInfo.tintIndex() &&
                    shade == srcInfo.shade() &&
                    lightEmission == srcInfo.lightEmission();
            if (canReuseMaterialInfo) {
                materialInfo = srcInfo;
            } else {
                materialInfo = new BakedQuad.MaterialInfo(sprite, chunkLayer, itemRenderType, tintIndex, shade, lightEmission);
            }


        } else {
            pos0 = new Vector3f(positions[0]);
            pos1 = new Vector3f(positions[1]);
            pos2 = new Vector3f(positions[2]);
            pos3 = new Vector3f(positions[3]);
            materialInfo = new BakedQuad.MaterialInfo(sprite, chunkLayer, itemRenderType, tintIndex, shade, lightEmission);
        }

        return new BakedQuad(
                pos0,
                pos1,
                pos2,
                pos3,
                uvs[0],
                uvs[1],
                uvs[2],
                uvs[3],
                direction,
                materialInfo);
    }

    /**
     * Tries to reuse the position vectors of the last quad we sourced any data from.
     * This avoids unnecessary allocations if the positions of the quad were not transformed,
     * or if a rotation simply rotated the order of positions.
     */
    private static Vector3fc reuseVector(BakedQuad quad, Vector3f position) {
        for (int i = 0; i < 4; i++) {
            if (quad.position(i).equals(position)) {
                return quad.position(i);
            }
        }
        return new Vector3f(position); // Copy if reuse is not possible
    }



    /**
     * {@return a copy of this mutable quad}
     */
    public MutableQuad copy() {
        return copyInto(new MutableQuad());
    }

    /**
     * Copies the contents of this mutable quad into the provided mutable quad and returns it.
     */
    public MutableQuad copyInto(MutableQuad dest) {
        for (int i = 0; i < 4; i++) {
            dest.positions[i].set(positions[i]);
        }
        System.arraycopy(uvs, 0, dest.uvs, 0, uvs.length);
        System.arraycopy(normals, 0, dest.normals, 0, normals.length);
        System.arraycopy(colors, 0, dest.colors, 0, colors.length);
        dest.direction = direction;
        dest.sprite = sprite;
        dest.chunkLayer = chunkLayer;
        dest.itemRenderType = itemRenderType;
        dest.tintIndex = tintIndex;
        dest.shade = shade;
        dest.lightEmission = lightEmission;
        dest.ambientOcclusion = ambientOcclusion;
        dest.lastSourceQuad = lastSourceQuad;
        return dest;
    }

    public MutableQuad reset() {
        for (int i = 0; i < 4; i++) {
            positions[i].set(0, 0, 0);
        }
        Arrays.fill(uvs, 0L);
        Arrays.fill(normals, 0);
        Arrays.fill(colors, 0xFFFFFFFF);
        direction = Direction.DOWN;
        sprite = null;
        chunkLayer = null;
        itemRenderType = null;
        tintIndex = -1;
        shade = true;
        lightEmission = 0;
        ambientOcclusion = false;
        lastSourceQuad = null;

        return this;
    }
}
