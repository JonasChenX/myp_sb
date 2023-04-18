package com.jonas.myp_sb.example.query;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

public class Query {
    private static final Pattern PARAMETER_NAME_PATTERN = Pattern.compile("\\w+");
    private static final Pattern STRING_LITERAL_PATTERN = Pattern.compile("'(?:[^']|'')*'");
    private static final Pattern PARAMETER_PATTERN;
    private static final Pattern COMMENT_PATTERN;
    private static final Pattern STRING_TOKENIZER_PATTERN;
    private final String string;
    private final Map<String, Object> parameters;

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(@Language("SQL") @NotNull String string, Object... parameters) {
        return (new Builder()).append(string, parameters);
    }

    public static Query of(@Language("SQL") @NotNull String queryString, Map<String, ?> parameters) {
        return new Query(queryString, parameters);
    }

    public static Query of(@Language("SQL") @NotNull String queryString) {
        return new Query(queryString);
    }

    public static Query of(@Language("SQL") @NotNull String queryString, Object... parameters) {
        return (new Builder()).append(queryString, parameters).build();
    }

    Query(@Language("SQL") @NotNull String string) {
        this(string, (Map)null);
    }

    Query(@Language("SQL") @NotNull String string, Map<String, ?> parameters) {
        if (string != null && !string.trim().isEmpty()) {
            this.string = string;
            if (parameters == null) {
                this.parameters = Collections.emptyMap();
            } else {
                this.parameters = new LinkedHashMap(parameters);
            }

            this.assertAllParametersProvided();
        } else {
            throw new IllegalArgumentException("Parameter \"string\" must not be null or empty.");
        }
    }

    private void assertAllParametersProvided() {
        List<String> unresolveds = new ArrayList();
        Set<String> parameterNames = new LinkedHashSet();
        extractAndPopulateParameterNames(this.string, parameterNames);
        Iterator var3 = parameterNames.iterator();

        while(var3.hasNext()) {
            String parameterName = (String)var3.next();
            if (!this.parameters.containsKey(parameterName)) {
                unresolveds.add(parameterName);
            }
        }

        if (!unresolveds.isEmpty()) {
            String message = String.format("Parameter(s) %1$s are unresolvable in query string \"%2$s\" from given parameters: %3$s", unresolveds, this.string, this.parameters);
            throw new IllegalArgumentException(message);
        }
    }

    private static List<String> extractAndPopulateParameterNames(CharSequence queryString, Collection<String> container) {
        List<String> tokenizedQuery = new ArrayList();
        Matcher matcher = STRING_TOKENIZER_PATTERN.matcher(queryString);
        StringBuilder token = new StringBuilder();

        while(matcher.find()) {
            String parameterName = matcher.group("parameterName");
            String stringLiteral = matcher.group("stringLiteral");
            String comment = matcher.group("comment");
            String rest = matcher.group("rest");
            if (parameterName != null) {
                container.add(parameterName);
                tokenizedQuery.add(token.toString());
                token = new StringBuilder();
            } else if (stringLiteral != null) {
                token.append(stringLiteral);
            } else if (comment != null) {
                token.append(comment);
            } else {
                token.append(rest);
            }
        }

        if (token.length() != 0) {
            tokenizedQuery.add(token.toString());
        }

        return tokenizedQuery;
    }

    public String getString() {
        return this.string;
    }

    public Map<String, Object> getParameters() {
        return this.parameters;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Query)) {
            return false;
        } else {
            Query other = (Query)o;
            Object this$string = this.getString();
            Object other$string = other.getString();
            if (this$string == null) {
                if (other$string != null) {
                    return false;
                }
            } else if (!this$string.equals(other$string)) {
                return false;
            }

            Object this$parameters = this.getParameters();
            Object other$parameters = other.getParameters();
            if (this$parameters == null) {
                if (other$parameters != null) {
                    return false;
                }
            } else if (!this$parameters.equals(other$parameters)) {
                return false;
            }

            return true;
        }
    }

    public int hashCode() {
        int result = 1;
        Object $string = this.getString();
        result = result * 59 + ($string == null ? 43 : $string.hashCode());
        Object $parameters = this.getParameters();
        result = result * 59 + ($parameters == null ? 43 : $parameters.hashCode());
        return result;
    }

    public String toString() {
        return "Query(string=" + this.getString() + ", parameters=" + this.getParameters() + ")";
    }

    static {
        PARAMETER_PATTERN = Pattern.compile(":(" + PARAMETER_NAME_PATTERN.pattern() + ")");
        COMMENT_PATTERN = Pattern.compile("--[^\r\n]*");
        STRING_TOKENIZER_PATTERN = Pattern.compile("(?<stringLiteral>" + STRING_LITERAL_PATTERN + ")|(?::(?<parameterName>" + PARAMETER_NAME_PATTERN + "))|(?<comment>(" + COMMENT_PATTERN + ")\n*)|(?<rest>((?!(--|:|')).)+)", 32);
    }

    public static class Builder {
        final StringBuilder string = new StringBuilder();
        Map<String, Object> parameters = new LinkedHashMap();
        private boolean expandIterableParameters = true;
        private boolean addSpaceBetweenAppends = true;

        public Builder() {
        }

        public Builder expandIterableParameters(boolean expandIterableParameters) {
            this.expandIterableParameters = expandIterableParameters;
            return this;
        }

        public boolean isExpandIterableParameters() {
            return this.expandIterableParameters;
        }

        public Builder addSpaceBetweenAppends(boolean addSpaceBetweenAppends) {
            this.addSpaceBetweenAppends = addSpaceBetweenAppends;
            return this;
        }

        public boolean isAddSpaceBetweenAppends() {
            return this.addSpaceBetweenAppends;
        }

        void assertParameterNameValid(String parameterName) {
            if (!Query.PARAMETER_NAME_PATTERN.matcher(parameterName).matches()) {
                String message = String.format("Parameter name \"%1$s\" is invalid.", parameterName);
                throw new IllegalArgumentException(message);
            }
        }

        public Builder appendWhen(boolean test, @Language("SQL") String queryString, Object... parameters) {
            return test ? this.append(queryString, parameters) : this;
        }

        public Builder append(@Language("SQL") String queryString, Object... parameters) {
            Objects.requireNonNull(queryString, "Parameter \"queryString\" must not be null.");
            if (this.isAddSpaceBetweenAppends() && this.string.length() > 0 && queryString.length() > 0 && queryString.charAt(0) != ' ' && this.string.charAt(this.string.length() - 1) != ' ') {
                this.string.append(' ');
            }

            if (parameters != null && parameters.length > 0) {
                this.appendAndPutParameters(this.string, queryString, parameters);
            } else {
                this.string.append(queryString);
            }

            return this;
        }

        private void appendAndPutParameters(StringBuilder queryString, @Language("SQL") String queryFragment, Object... parameters) {
            List<String> parameterNames = new ArrayList();
            Iterator<String> nonParameterParts = Query.extractAndPopulateParameterNames(queryFragment, parameterNames).iterator();
            if (parameterNames.size() != parameters.length) {
                String message = String.format("Query string \"%1$s\" contains %2$d parameter(s) %3$s, and the size was mismatched to the given parameter(s) of %4$d containing %5$s", queryFragment, parameterNames.size(), parameterNames, parameters.length, Arrays.deepToString(parameters));
                throw new IllegalArgumentException(message);
            } else {
                queryString.append((String)nonParameterParts.next());
                int i = 0;

                for(int n = parameterNames.size(); i < n; ++i) {
                    String currentParameterName = (String)parameterNames.get(i);
                    Object currentParameters = parameters[i];
                    if (this.isExpandIterableParameters() && currentParameters instanceof Iterable) {
                        Iterable<?> values = (Iterable)currentParameters;
                        this.expandIterable(queryString, currentParameterName, values);
                    } else {
                        queryString.append(":").append(currentParameterName);
                        this.put(currentParameterName, currentParameters);
                    }

                    if (nonParameterParts.hasNext()) {
                        queryString.append((String)nonParameterParts.next());
                    }
                }

            }
        }

        private void expandIterable(StringBuilder queryString, String namePrefix, Iterable<?> values) {
            List<String> names = new ArrayList();
            int counter = 1;

            for(Iterator var6 = values.iterator(); var6.hasNext(); ++counter) {
                Object value = var6.next();
                String name = namePrefix + counter;
                names.add(":" + name);
                this.put(name, value);
            }

            queryString.append(String.join(", ", names));
        }

        public Builder put(String parameterName, Object parameterValue) {
            this.assertParameterNameValid(parameterName);
            this.parameters.put(parameterName, parameterValue);
            return this;
        }

        public Builder putAll(Map<String, ?> parameters) {
            Iterator var2 = parameters.entrySet().iterator();

            while(var2.hasNext()) {
                Map.Entry<String, ?> parameter = (Map.Entry)var2.next();
                this.put((String)parameter.getKey(), parameter.getValue());
            }

            return this;
        }

        public Query build() {
            return new Query(this.string.toString(), this.parameters);
        }
    }
}
