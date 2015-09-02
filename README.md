# Realm-service
Using service pattern with realm as DB and retrofit as service client.

Pattern:


Intent service looks really good to me for Rest API calls from retrofit.Use synchronous method. For retrofitwe can add interceptor for 3 retry for all http network request. http://stackoverflow.com/questions/24562716/how-to-retry-http-requests-with-okhttp-retrofit. https://github.com/square/retrofit/issues/297 We can then use broadcast manager to broadcast the data and subscribe the activities with it. Also in catch retrofit error - we can log events. Before starting the request we add the entry in db with flag inprogress=true (if it doesn't exist) and once the request finish in finally we can set flag to false.

    Activity in foreground - call started flag true. Call finished broadcast event - activity still in foreground broadcast received - done.
    Activity went to background and the call returned - we insert in db and set progress flag to false/complete. Next time activity comes on front and it reads from db if the flag is oncomplete.
    Activity went to background and came to foreground again - call completes - broadcast receiver listens. It will not retry since flag is inprogress. If it is incomplete then retries.
    In case the network is not avaiable the request is not started and flag is false.
    In case there are networks errors in between request is getting executed in catch exception there will be retries and status will be set to incomplete after 3 retries. The next time activity starts and if the flag is incomplete and it will retry.


