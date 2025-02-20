import java.net.*;
import java.net.http.*;

public class Bot {
    public static void main(String[] args) {
        String webhookUrl = System.getenv("SLACK_WEBHOOK_URL");
        String message = System.getenv("SLACK_WEBHOOK_MSG"); //환경변수 수정

        if (webhookUrl == null || webhookUrl.isEmpty()) {
            System.err.println("SLACK_WEBHOOK_URL 환경 변수가 설정되지 않았습니다.");
            return;
        }

        if (message == null || message.isEmpty()) {
            System.err.println("SLACK_WEBHOOK_MSG 환경 변수가 설정되지 않았습니다.");
            return;
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(webhookUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"text\":\"" + message + "\"}")) // 메시지 구성 수정
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("요청 코드: " + response.statusCode());
            System.out.println("응답 결과: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}