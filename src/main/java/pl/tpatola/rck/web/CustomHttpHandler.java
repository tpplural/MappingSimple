package pl.tpatola.rck.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import pl.tpatola.rck.common.TaskConstants;
import pl.tpatola.rck.logic.SolutionProvider;
import pl.tpatola.rck.logic.SolutionProviderImpl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomHttpHandler implements HttpHandler {
    private static final String PARAM_CATEGORY = "category";
    private static final String PARAM_VALUE = "value";

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {


        String requestParamValue = null;
        if ("GET".equals(httpExchange.getRequestMethod())) {
            handleGetRequest(httpExchange);

        } else {
            throw new UnsupportedOperationException();
        }


    }

    private void handleGetRequest(HttpExchange httpExchange) throws IOException  {

        String params = httpExchange.getRequestURI().toString();

        String category = Stream.of(params.split("\\?")[1].split("&"))
                .map(kv -> kv.split("="))
                .filter(kv -> PARAM_CATEGORY.equalsIgnoreCase(kv[0]))
                .map(kv -> kv[1])
                .findFirst()
                .orElse("");

        List<Integer> values = Stream.of(params.split("\\?")[1].split("&"))
                .map(kv -> kv.split("="))
                .filter(kv -> PARAM_VALUE.equalsIgnoreCase(kv[0]))
                .map(kv -> Integer.valueOf(kv[1]))
                .collect(Collectors.toList());

        List<String> validationIssues = validateInput(category, values);
        //SolutionProviderImpl.getInstance().
        if (validationIssues.size() > 0){
            handleErrorResponse(httpExchange,validationIssues);
        }

        SolutionProvider solutionProvider = SolutionProviderImpl.getInstance();
        Map<Integer,List<String>> mappings = solutionProvider.getDividersWithMappings(values,category);

        handleResponse(httpExchange, mappings);


    }

    private List<String> validateInput(String category, List<Integer> values) {
        List<String> result = new ArrayList<>();
        if (category == null || category.isEmpty()) {
            result.add(String.format("Category parameter value is required."));
        } else if (!TaskConstants.SUPPORTED_COLLECTIONS.contains(category)) {
            result.add(String.format("Category parameter value [%s] is not known.", category));

        }

        if (values == null || values.isEmpty()) {
            result.add(String.format("value parameter  is required."));
        } else {
            values.stream()
                    .filter(val -> val > TaskConstants.MAXIMUM_MAPPING_RANGE || val < 1)
                    .forEach(val -> result.add(String.format("parameter value of %s is invalid.", val)));
        }

        return result;
    }

    private void handleErrorResponse(HttpExchange httpExchange, List<String> validationErrors) throws IOException {
        OutputStream outputStream = httpExchange.getResponseBody();

        StringBuilder htmlBuilder = new StringBuilder();

        htmlBuilder.append("<html>").append("<body>").append("<h1>");
        validationErrors.forEach(t->    htmlBuilder.append(t + "<br>"));
        htmlBuilder.append("</h1>").append("</body>").append("</html>");
        // encode HTML content
        String htmlResponse = htmlBuilder.toString();
        //String htmlResponse = StringEscapeUtils.escapeHtml4(htmlBuilder.toString());
        // this line is a must
        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();


    }

    private void handleResponse(HttpExchange httpExchange, Map<Integer,List<String>> responseContent) throws IOException {

        OutputStream outputStream = httpExchange.getResponseBody();
        StringBuilder htmlBuilder = new StringBuilder();

        htmlBuilder.append("<html>").append("<body>").append("<h1>").append("Hello <br>");
        responseContent.forEach((k,v) ->   {
            htmlBuilder.append(k +" ->" + String.join(",",v)+ "<br>");
        });
        htmlBuilder.append("</h1>").append("</body>").append("</html>");
        // encode HTML content

        String htmlResponse = htmlBuilder.toString();
        //String htmlResponse = StringEscapeUtils.escapeHtml4(htmlBuilder.toString());
        // this line is a must

        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();

    }

}
