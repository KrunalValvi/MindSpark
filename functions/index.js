const functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp();

exports.sendChatNotification = functions.database.ref('/notifications/{receiverEmail}/{notificationId}')
    .onCreate(async (snapshot, context) => {
        const notificationData = snapshot.val();
        const { token, data } = notificationData;

        // Create the notification message
        const message = {
            token: token,
            data: data,
            android: {
                priority: 'high',
                notification: {
                    channelId: 'chat_messages',
                    title: data.title,
                    body: data.message,
                    clickAction: 'FLUTTER_NOTIFICATION_CLICK',
                    priority: 'high',
                    sound: 'default'
                }
            }
        };

        try {
            // Send the notification
            const response = await admin.messaging().send(message);
            console.log('Successfully sent notification:', response);

            // Delete the notification document after sending
            return snapshot.ref.remove();
        } catch (error) {
            console.error('Error sending notification:', error);
        }
    });