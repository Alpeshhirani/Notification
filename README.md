# Notification



# Add code in AndroidManifest
`````
 service android:name=".fcm.MyFirebaseMessagingService">
            <intent-filter>
                <action android:name="com.google.firebase.MESSAGING_EVENT" />
            </intent-filter>
        </service>

        <service android:name=".fcm.MyFirebaseInstanceIDService">
            <intent-filter>
                <action android:name="com.google.firebase.INSTANCE_ID_EVENT" />

                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
        </service>
        
       
