package fr.vadimsoude;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/repo")
public class repo {

    @GetMapping("/{word}")
    private String getDefinition(@PathVariable(value = "word")  String word) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.dictionaryapi.dev/api/v2/entries/en/" + word)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        return response.body().string().split("definition" )[2].replace(":","").replace("\"","").split("\\.")[0];
    }
}
