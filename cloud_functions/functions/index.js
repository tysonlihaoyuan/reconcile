const functions = require('firebase-functions');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
const admin = require('firebase-admin');
admin.initializeApp();

exports.notifyNewMessage = functions.firestore
    .document('chat/{channel}/chatHistory/{message}')
    .onCreate((docSnapshot, context) => {
      const message = docSnapshot.data()
      const messageContent = message['message']
      const channelUID = context.params.channel
      console.log('chatroom ' + channelUID + 'has changed');
      const sender = message['ownerName']
      const payload = {
                notification: {
                    title: "One of the chatroom you subscribed has a new message",
                    body: sender + "says " + messageContent
                }
            }
      console.log(messageContent);
      return admin.firestore().doc('chat/' + channelUID).get().then(chatroom =>
          {
            const subscribersUIDs = chatroom.get('subscribedUserUID')
            subscribersUIDs.forEach((item, i) => {
              console.log('user id ' + item + 'has a new message');
              return admin.firestore().doc('users/' + item).get().then(userDoc => {
                     const registrationTokens = userDoc.get('registrationTokens')
                     return admin.messaging().sendToDevice(registrationTokens,payload).then(response =>{
                         return console.log("message sent");
                       })
                   }
               )
            });
            return 1;

          }
      )
/*      return admin.firestore().doc('users/' + 'iseyPC0o6vPoUBK3gm18qOy7GcC3').get().then(userDoc => {
        const registrationTokens = userDoc.get('registrationTokens')
        return admin.messaging().sendToDevice(registrationTokens,payload).then(response =>{
            return console.log("message sent");
          })
      }
  )*/
})
