package com.dotsub.converter.model;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-15.
 */
public class Configuration {

    //frame-rates
    private Double importFps = null;
    private Double exportFps = null;

    //for shifting subtitles
    private Integer offset = null;

    //Export look and feel settings (not applicable in all formats)
    //Fonts
    private String font = null;
    private String fontSize = null;
    //Position
    private HorizontalPosition horizontalPosition = HorizontalPosition.CENTER;
    private VerticalPosition verticalPosition = VerticalPosition.BOTTOM;
    //Color (0x000000 to 0xFFFFFF) I might replace this with java.awt.Color
    private Integer color = null;
    private Integer backgroundColor = null;
    //0x00 to 0xFF
    private Integer backgroundAlpha = null;

    public Configuration() {
    }

    public Double getImportFps() {
        return importFps;
    }

    public void setImportFps(Double importFps) {
        this.importFps = importFps;
    }

    public Double getExportFps() {
        return exportFps;
    }

    public void setExportFps(Double exportFps) {
        this.exportFps = exportFps;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public HorizontalPosition getHorizontalPosition() {
        return horizontalPosition;
    }

    public void setHorizontalPosition(HorizontalPosition horizontalPosition) {
        this.horizontalPosition = horizontalPosition;
    }

    public VerticalPosition getVerticalPosition() {
        return verticalPosition;
    }

    public void setVerticalPosition(VerticalPosition verticalPosition) {
        this.verticalPosition = verticalPosition;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public Integer getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Integer backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Integer getBackgroundAlpha() {
        return backgroundAlpha;
    }

    public void setBackgroundAlpha(Integer backgroundAlpha) {
        this.backgroundAlpha = backgroundAlpha;
    }
}
