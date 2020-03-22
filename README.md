bitcapital-java-sdk
===================

**ALPHA NOTICE:** This SDK is considered experimental and may lack features and support from the other official SDKs.  

<br />

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

The Bitcapital Java SDK aims to be a thin layer over the [OkHttp Client](https://square.github.io/okhttp), so you can manipulate and intercept the requests.
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

Fetch the currently authenticated user through the API using a POJO model. The SDK uses the [Gson library](https://github.com/google/gson) under the hood for handling JSON objects.

```java
// You can use one of the built-in models or write your own
UserModel user = bitcapital.getClient().get("/users/me", UserModel.class);

if (user != null) {
    System.out.println("User ID: " + user.id);
}
```

#### Mapping the API endpoints using Retrofit templates

The SDK supports [Retrofit](https://square.github.io/retrofit) templates out-of-the box.

```java
// Maps the API endpoints using Retrofit annotations
public interface StatusWebService {
    
    @GET("status")
    Call<ServerStatus> current();

}

// Register the template in the Bitcapital OkHttp client and request the resource from the API
// Uses the `retrofit.create(Class<T> cls)` method to bind to current OkHttp client instance
StatusWebService ws = bitcapital.getClient().retrofit(StatusWebService.class);
ServerStatus status = ws.status().execute();
```

<br />

## Sample Scripts

The repository comes with a set of sample routines to test the SDK and validate your set of credentials. Start by copying
the `.env.sample` file in the root as `.env` and inputting your credentials.

Then start the scripts using gradle:

```bash
./gradlew run
```

<br />

## Request Signing

The Bitcapital SDK handles automatically the request signature when the OkHttp client is used in requests from the `bitcapital.getClient()` method.

To generate a Request Signature manually using HMAC SHA256, you can use the `RequestSigning` class, although the official recommendation is to let the SDK deal with authentication and signing for the requests.

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

<br />

## Roadmap

<br />

- [x] Base SDK
    - [x] Bitcapital singleton wrapper
    - [x] OkHttp initialization
    - [x] Retrofit initialization
    - [x] Server status requests
   
<br />

- [x] Request Signing
    - [x] Base request signing request
    - [x] OkHttp signing interceptor
<br /> 

- [x] Rest API    
    - [x] Calling API using hashmaps
    - [x] Calling API using Gson classes
    - [x] Calling API using Retrofit interfaces

<br />

- [ ] OAuth 2.0
    - [x] Credential request (/oauth/token)
    - [x] OkHttp bearer credentials interceptor
    - [ ] OkHttp refresh token interceptor

<br />
