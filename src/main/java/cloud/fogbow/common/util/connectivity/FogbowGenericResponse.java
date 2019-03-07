package cloud.fogbow.common.util.connectivity;

import java.util.Objects;

public class FogbowGenericResponse {
    private String content;

    public FogbowGenericResponse(String content) {
        this.content = content;
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
        FogbowGenericResponse that = (FogbowGenericResponse) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    @Override
    public String toString() {
        return "FogbowGenericResponse{" +
                "content='" + content + '\'' +
                '}';
    }
}
