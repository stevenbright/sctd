package sctd.model;

/**
 * Isometric tile
 * http://flarerpg.org/tutorials/isometric_intro/
 *
 * Camera: Camera angle (60, 0, 45) for video-game style isometric (tiles that are 2x wide as they are tall)
 *
 * @author Alexander Shabanov
 */
public final class GameField {
  public enum Tile {
    EMPTY,
    DARK_GRAY
  }

  private final int tileWidth;
  private final int tileHeight;
  private final int width;
  private final int height;
  private final Tile[] tiles;

  public GameField(int width, int height) {
    this.tileWidth = 64;
    this.tileHeight = 64;
    this.width = width;
    this.height = height;
    final int tileCount = width * height;
    this.tiles = new Tile[tileCount];

    for (int i = 0; i < tileCount; ++i) {
      this.tiles[i] = Tile.EMPTY;
    }

    setTile(2, 3, Tile.DARK_GRAY);
  }

  public int getTileWidth() {
    return tileWidth;
  }

  public int getTileHeight() {
    return tileHeight;
  }

  public int getHorizontalTileCount() {
    return width;
  }

  public int getVerticalTileCount() {
    return height;
  }

  public int getWidth() {
    return getHorizontalTileCount() * getTileWidth();
  }

  public int getHeight() {
    return getVerticalTileCount() * getTileHeight();
  }

  public Tile getTile(int x, int y) {
    return tiles[getTileIndex(x, y)];
  }

  public void setTile(int x, int y, Tile tile) {
    tiles[getTileIndex(x, y)] = tile;
  }

  //
  // Private
  //

  private int getTileIndex(int x, int y) {
    if (x < 0 || x >= getHorizontalTileCount() || y < 0 || y >= getVerticalTileCount()) {
      throw new IndexOutOfBoundsException("x=" + x + ", y=" + y);
    }

    return y * getHorizontalTileCount() + x;
  }
}
