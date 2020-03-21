bitcapital-java-sdk
===================

## Getting Started

Initialize your Bit Capital client.

```java
// Initialize your client SDK instance
Bitcapital bitcapital = Bitcapital.initialize(apiUrl, clientId, clientSecret);

// Authenticate using OAuth 2.0
OAuthCredentials credentials = bitcapital.oauth().token()
    .execute()
    .body();

System.out.println(credentials.accessToken);
```

<br />

## API Requests

The Bitcapital Java SDK aims to be a thin layer over OkHttp client, so you can manipulate and intercept the requests.
 To request a resource from the API you can use one of 3 methods: 

#### TLDR: Calling the API directly

Fetch the currently authenticated user through the API using a raw HashMap.

```java
// You can use one of the built-in models or write your own
HashMap user = bitcapital.getClient().get("/users/me", HashMap.class);

if (user != null) {
    System.out.println("User ID: " + user.get("id").toString());
}
```

#### Calling the API directly using Java models

Fetch the currently authenticated user through the API using a POJO model.

```java
// You can use one of the built-in models or write your own
UserModel user = bitcapital.getClient().get("/users/me", UserModel.class);

if (user != null) {
    System.out.println("User ID: " + user.id);
}
```

#### Mapping the API endpoints using Retrofit templates

```java
// Maps the API endpoints using Retrofit annotations
public interface StatusWebService {
    
    @GET("status")
    Call<ServerStatus> current();

}

// Register the template in the Bitcapital OkHttp client and request the resource from the API
StatusWebService ws = bitcapital.getClient().retrofit(StatusWebService.class);
ServerStatus status = ws.status().execute();
```

The SDK uses the Gson library under the hood, if you need additional information check the `bitcapital.getClient()` instance.

<br />

## Sample Scripts

The repository comes with a set of sample routines to test the SDK and validate your set of credentials. Start by copying
the `.env.sample` file in the root as `.env` and inputting your credentials.

Then start the scripts using gradle:

```bash
./gradlew run
```

## Request Signing

The Bitcapital SDK handles automatically the request signature when the OkHttp client is used. 
To generate a Request Signature manually using HMAC SHA256.

```java
String method = request.method().toUpperCase();
String url = "/" + String.join("/", request.url().pathSegments());
String body = null;

if ((method.equals("POST") || method.equals("PUT")) && request.body() != null) {
    body = this.bodyToString(request.body());
}

long now = System.currentTimeMillis();
String signature = new RequestSigning().sign(bitcapital.getClientSecret(), method, url, body, now);
```

<br />

## Development

Compile and run tests using Gradle:

```bash
./gradlew clean build
```

Execute sample routine from main class.

```bash
./gradlew run
```