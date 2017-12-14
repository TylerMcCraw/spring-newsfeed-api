# spring-newsfeed-api
NewsFeed Rest API built on Sprint Boot, Exposed, and Kotlin

Data Structure:
Currently, there are data models for Users, Friend Relationships, and Posts.
These are provided as flat models (there is no nesting), but they may be nested in API responses.
However, in a real environment, there are going to be many more data models.

APIs Provided:
Currently, there is only one API in [PostController](https://github.com/TylerMcCraw/spring-newsfeed-api/blob/master/src/main/kotlin/com/w3bshark/newsfeed/web/PostController.kt) for the user's news "feed" - list of posts that may be intelligently paginated
However, in a real environment, there will be APIs for OAuth2 authentication, User APIs, Configuration/Setting APIs, etc.

The PostController `feed` API returns a list of posts with a poster user object nested in it. This returns the exact amount of information that a mobile client would need and it returns it in a structure that is easy to parse. Endpoints for web clients should be separate from this API endpoint because more often than not web clients will need more data returned (because web clients can display more information at once).

Goals:
Backend API
1. Only send data that needs to be sent (null fields can be removed)
2. DateTime field on all posts based in UTC time. Server also needs to be set to UTC time.
3. DateTimes will be returned in API calls as a string with ISO8601 format.
4. Even if data fields are not a part of a stored “Post” object, if the mobile application needs a field for a Posts specific UI piece, then the API should return the field (at least for the Mobile API) so that the application doesn’t have to make successive calls to get additional information. This will reduce the amount of time the user has to wait and will require less network traffic, all of which are important because of the user’s time and money (extra network calls = more data used on the user’s data plan, if on cellular).
5. URLs shouldn’t contain ID of user or any identifiable information. This was only here to test APIs for different users. This would normally be covered by OAuth and the APIs would only return information for the authorized user in the request.
6. API could return metadata for images/media content so that app can at least load frame of image or API could return link to compressed, low-res image to load first
7. In a real API, there would be more concern around access control and so there would be more logic in place to ensure that consumers of this API can only access information that they are allowed to access.
