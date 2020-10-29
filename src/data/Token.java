package data;

public class Token {
    String token;

    public Token(DataType data) {
        String payload = data.getPayload();
        this.token = payload.substring(0,6);
    }

    public String getToken() {
        return token;
    }
}
