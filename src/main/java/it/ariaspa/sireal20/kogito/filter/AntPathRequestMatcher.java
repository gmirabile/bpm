package it.ariaspa.sireal20.kogito.filter;

public class AntPathRequestMatcher {

    private final String pattern;

    public AntPathRequestMatcher(String pattern) {
        this.pattern = pattern;
    }
    public boolean matches(String path) {
        // Implement your matching logic here
        if (this.pattern.equals("/**")) {
            return true;
        }
        return path.matches(pattern.replace("**", ".*").replace("*", "[^/]*"));
    }

    public String getPattern() {
        return pattern;
    }
}
