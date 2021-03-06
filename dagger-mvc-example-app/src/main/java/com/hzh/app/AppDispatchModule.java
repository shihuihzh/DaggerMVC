package com.hzh.app;

import com.hzh.dagger.annotation.DispatchPath;
import com.hzh.dagger.http.*;
import com.hzh.dagger.module.CollectDispatcherModule;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Module(includes = {CollectDispatcherModule.class, AppErrorHandlerModule.class })
final public class AppDispatchModule {
    private static final Logger logger = LoggerFactory.getLogger(AppDispatchModule.class);

    @Provides
    @IntoMap
    @DispatchPath(value = "/hello", method = HttpMethod.GET)
    static Result dispatchHello(Request request, HttpMethod method, QueryRequestParameters parameters) {
        logger.info("calling hello {}", request.getRawRequest().uri());
        logger.info("Method: {}", method);
        logger.info("Request parameter: {}", parameters);
        return HTMLResult.newBuilder()
                .withStatusCode(200)
                .withHTML("<h1>Hello</h1>")
                .build();
    }

    @Provides
    @IntoMap
    @DispatchPath("/world")
    static Result dispatchWorld(Request request) {
        logger.info("calling world");
        try {
            return HTMLResult.newBuilder()
                    .withStatusCode(200)
                    .withHTML(request.getRawRequest().readBodyAsString())
                    .build();
        } catch (IOException e) {
            return DaggerMVCException.createAndThrow(e);
        }
    }

    @Provides
    @IntoMap
    @DispatchPath("/exception")
    static Result dispatchException(Request request) {
        logger.info("calling throw exception");
        return DaggerMVCException.createAndThrow(new RuntimeException());
    }

    @Provides
    @IntoMap
    @DispatchPath("/hello/{name}/{age}/{hobby}")
    static Result dispatchHelloName(Request request, PathValueMap pathValue) {
        logger.info("calling helloName");
        return HTMLResult.newBuilder()
                .withStatusCode(200)
                .withHTML(pathValue.toString())
                .build();
    }

    @Provides
    @IntoMap
    @DispatchPath("/blank")
    static Result dispatchBlank() {
        logger.info("calling empty");
        return HTMLResult.newBuilder()
                .withStatusCode(200)
                .withHTML("Hello")
                .build();
    }

    @Provides
    @IntoMap
    @DispatchPath("/cookie")
    static Result dispatchCookie(Request request, List<Cookie> cookieList) {
        logger.info("calling cookie");
        StringBuilder sb = new StringBuilder();
        sb.append("<h1>Cookie</h1>");
        cookieList.forEach(c -> sb.append(c.getName()).append(": ").append(c.getValue()).append("<br>"));

        // set a random cookie
        Cookie cookie = Cookie.newBuilder()
                .withName("randomNumber")
                .withValue(String.valueOf(ThreadLocalRandom.current().nextInt(10000)))
                .withMaxAge(ThreadLocalRandom.current().nextInt(10000))
                .build();

        return HTMLResult.newBuilder()
                .withStatusCode(200)
                .withHTML(sb.toString())
                .withCookie(cookie)
                .build();
    }

    @Provides
    @IntoMap
    @DispatchPath("/json")
    static Result dispatchJson() {
        logger.info("calling json");
        Map<String, Object> obj = new HashMap<>();
        obj.put("name", "Howe");
        obj.put("age", 30);
        obj.put("hobby", "computer");

        return JSONResult.newBuilder()
                .withStatusCode(200)
                .withObject(obj)
                .build();

    }

    @Provides
    @IntoMap
    @DispatchPath("/attributes")
    static Result dispatchAttributes(Request request) {
        logger.info("calling attr");
        request.setAttribute("name", "Howe");
        request.setAttribute("age", 30);

        return JSONResult.newBuilder()
                .withStatusCode(200)
                .withObject(request.getAllAttributes())
                .build();

    }

    @Provides
    @IntoMap
    @DispatchPath("/download")
    static Result dispatchDownload() {
        logger.info("calling download");
        return FileResult.newBuilder()
                .withStatusCode(200)
                .withFile(new File(AppDispatchModule.class.getResource("/downloadme.bin").getFile()))
                .build();
    }

    @Provides
    @IntoMap
    @DispatchPath("/download-mp4")
    static Result dispatchDownloadMP4() {
        logger.info("calling download");
        return FileResult.newBuilder()
                .withStatusCode(200)
                .withFile(new File(AppDispatchModule.class.getResource("/downloadme.mp4").getFile()))
                .build();
    }

    @Provides
    @IntoMap
    @DispatchPath(value = "/upload-with-form", method = HttpMethod.POST)
    static Result UploadWithForm(Request request, FormRequestParameters parameters) {
        logger.info("calling upload with form");

        try {
            final UploadedFile file = request.uploadedFile("file");
            return HTMLResult.newBuilder()
                    .withHTML(String.format("""
                            <h1>Upload Result</h1>
                            form data: %s <br>
                            file name: %s <br>
                            file size: %d <br>
                            upload file data: <br>
                            <textarea readonly>%s </textarea>
                            """, 
                            parameters,
                            file.filename(),
                            file.size(),
                            Base64.getEncoder().encodeToString(file.asBytes())))
                    .build();
        } catch (IOException e) {
            logger.error("upload failure. ", e);
            return HTMLResult.newBuilder()
                    .withHTML("upload failure")
                    .build();
        }
    }

    @Provides
    @IntoMap
    @DispatchPath("/redirect")
    static Result dispatchRedirect() {
        logger.info("calling redirect");
        return RedirectResult.newBuilder()
                .withUrl("/hello")
                .withText("go /hello")
                .build();
    }
}