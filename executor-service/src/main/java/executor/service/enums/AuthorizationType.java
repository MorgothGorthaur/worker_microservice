package executor.service.enums;

public enum AuthorizationType {
    BEARER("Bearer ");
    private final String prefix;

    AuthorizationType(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}