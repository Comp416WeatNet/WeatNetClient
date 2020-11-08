package data;

public  class Token {
    public static String token;

    public Token(DataType data) {
        String payload = data.getPayload();
        this.token = payload;
    }


}
