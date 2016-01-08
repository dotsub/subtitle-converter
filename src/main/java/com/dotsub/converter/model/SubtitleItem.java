package com.dotsub.converter.model;

import java.util.Objects;

/**
 * Represents a single line item from a caption or subtitle file.
 *
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-08.
 */
public class SubtitleItem {

    private int startTime;
    private int duration;
    private String content;

    /**
     * Creates a new SubtitleItem representing a single item from a file.
     *
     * @param startTime the time when the caption is shown in milliseconds.
     * @param duration the amount of time the caption is shown in milliseconds.
     * @param content The text content of the caption.
     */
    public SubtitleItem(int startTime, int duration, String content) {
        this.startTime = startTime;
        this.duration = duration;
        this.content = content;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubtitleItem that = (SubtitleItem) o;
        return Objects.equals(startTime, that.startTime) &&
                Objects.equals(duration, that.duration) &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, duration, content);
    }

    @Override
    public String toString() {
        return "SubtitleItem {" +
                "startTime=" + startTime +
                ", duration=" + duration +
                ", content='" + content + '\'' +
                '}';
    }
}
