package com.wunhwan.hawthorn.core.metadata;

import io.rsocket.metadata.WellKnownMimeType;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 */
public enum RSocketMimeType {

    JSON("json", WellKnownMimeType.APPLICATION_JSON);

    private final byte id;
    private final String name;
    private final String type;

    RSocketMimeType(String name, WellKnownMimeType mimeType) {
        this.id = mimeType.getIdentifier();
        this.name = name;
        this.type = mimeType.getString();
    }

    public byte getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "RSocketMimeType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
