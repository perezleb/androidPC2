How to Login
getSingleSignOnToken: This will give me the auth_token and cons_id (POST)
https://bvt100-secure.convio.com/bvt112/site/SRConsAPI?method=getSingleSignOnToken&api_key=api_key&v=1.0&login_name=kmartinez&login_password=kmartinez


HOME PAGE INFORMATION

How to get fundraising progress (GET)
getParticipantProgress: This will give me goal, amount raised, and percent raised
https://bvt100-secure.convio.com/bvt112/site/CRTeamraiserAPI?method=getParticipantProgress&api_key=api_key&v=1.0&cons_id=1001001&fr_id=1000


How to get list of recent gifts
getGifts: This wil give me a list of recent gifts (POST). Requires auth_token
https://bvt100-secure.convio.com/bvt112/site/CRTeamraiserAPI?method=getGifts&api_key=api_key&v=1.0&cons_id=1001001&fr_id=1000


ADD GIFT
addGift: (POST) Requires auth_token
https://secure2.convio.net/organization/site/CRTeamraiserAPI?method=addGift

To install maps Library: 
https://developers.google.com/maps/documentation/android/start#installing_the_google_maps_android_v2_api
Email leber.perez@blackbaud.com with your SHA key in order to use the same api key. 

