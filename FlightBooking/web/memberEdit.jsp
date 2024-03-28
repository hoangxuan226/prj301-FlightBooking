<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Member Edit</title>
        <link rel="stylesheet" href="./css/header.css" />
        <link rel="stylesheet" href="./css/aircraftDetails.css" />
    </head>
    <body>
        <div class="container">
            <%@include file="header.jsp" %>
            <div class="mainContent">
                <div class="tableContent">
                    <div class="planeList" id="planeListContent">
                        <div class="listHeader">
                            <p class="textHeader">Member Edit</p>
                        </div>
                        <form action="MemberController" method="post">
                            <table class="planeTbl"> 
                                <tr><td class="content">First Name</td><td><input name="firstname" value="${requestScope.member.firstname}" required></td></tr>	 
                                <tr><td class="content">Last Name</td><td><input name="lastname" value="${requestScope.member.lastname}" required></td></tr>	 
                                <tr><td class="content">Birthday</td><td><input type="date" name="birthday" value="${requestScope.member.birthday}" required></td></tr>	 
                                <tr>
                                    <td class="content">Gender</td>
                                    <td>
                                        <select name="gender" required>
                                            <option value="${requestScope.member.gender}" selected hidden>${requestScope.member.gender}</option>
                                            <option value="Female">Female</option>
                                            <option value="Male">Male</option>
                                            <option value="Other">Other</option>
                                        </select>
                                    </td>
                                </tr>	 
                                <tr><td class="content">Email</td><td><input name="email" value="${requestScope.member.email}" required></td><td>${requestScope.error}</td></tr>
                                <tr><td class="content">Phone Number</td><td><input name="phoneno" value="${requestScope.member.phoneno}" required></td></tr>	 
                                <tr>
                                    <td class="content">Nationality</td>
                                    <td>
                                        <select name="nationality" required>
                                            <option value="${requestScope.member.nationality}" selected hidden>${requestScope.member.nationality}</option>
                                            <c:forTokens var="nation" items="Afghanistan,Albania,Algeria,American Samoa,Andorra,Angola,Anguilla,Antarctica,Antigua and Barbuda,Argentina,Armenia,Aruba,Australia,Austria,Azerbaijan,Bahamas,Bahrain,Bangladesh,Barbados,Belarus,Belgium,Belize,Benin,Bermuda,Bhutan,Bolivia,Bonaire,Bosnia and Herzegovina,Botswana,Bouvet Island,Brazil,British Indian Ocean Territory,Brunei Darussalam,Bulgaria,Burkina Faso,Burundi,Cabo Verde,Cambodia,Cameroon,Canada,Cayman Islands,Central African Republic,Chad,Chile,China,Christmas Island,Cocos Islands,Colombia,Comoros,Congo,Cook Islands,Costa Rica,Croatia,Cuba,Curacao,Cyprus,Czechia,Denmark,Djibouti,Dominica,Dominican Republic,East Timor,Ecuador,Egypt,El Salvador,Equatorial Guinea,Eritrea,Estonia,Eswatini,Ethiopia,Falkland Islands,Faroe Islands,Fiji,Finland,France,French Guiana,French Polynesia,French Southern Territories,Gabon,Gambia,Georgia,Germany,Ghana,Gibraltar,Greece,Greenland,Grenada,Guadeloupe,Guam,Guatemala,Guernsey,Guinea,Guinea-Bissau,Guyana,Haiti,Heard Island and McDonald Islands,Holy See,Honduras,Hong Kong,Hungary,Iceland,India,Indonesia,Iran,Iraq,Ireland,Isle of Man,Israel,Italy,Jamaica,Japan,Jersey,Jordan,Kazakhstan,Kenya,Kiribati,Korea,Kuwait,Kyrgyzstan,Lao People's Democratic Republic,Latvia,Lebanon,Lesotho,Liberia,Libya,Liechtenstein,Lithuania,Luxembourg,Macao,Madagascar,Malawi,Malaysia,Maldives,Mali,Malta,Marshall Islands,Martinique,Mauritania,Mauritius,Mayotte,Mexico,Micronesia,Moldova,Monaco,Mongolia,Montenegro,Montserrat,Morocco,Mozambique,Myanmar,Namibia,Nauru,Nepal,Netherlands,NETHERLANDS ANTILLES,New Caledonia,New Zealand,Nicaragua,Niger,Nigeria,Niue,Norfolk Island,North Macedonia,Northern Mariana Islands,Norway,Oman,Pakistan,Palau,Palestine,Panama,Papua New Guinea,Paraguay,Peru,Philippines,PitCairn,Poland,Portugal,Puerto Rico,Qatar,Romania,Russia Federation,Rwanda,Reunion,Saint Barthelemy,Saint Helena,Saint Kitts and Nevis,Saint Lucia,Saint Martin,Saint Pierre and Miquelon,Saint Vincent and the Grenadines,Samoa,San Marino,Sao Tome and Principe,Saudi Arabia,Senegal,Serbia,Seychelles,Sierra Leone,Singapore,Sint Maarten,Slovakia,Slovenia,Solomon Islands,Somalia,South Africa,South Georgia and the South Sandwich Islands,South Sudan,Spain,Sri Lanka,Sudan,Suriname,Svalbard,Sweden,Switzerland,Syrian Arab Republic,Taiwan,Tajikistan,Tanzania,Thailand,Timor-Leste,Togo,Tokelau,Tonga,Trinidad and Tobago,Tunisia,Turkey,Turkmenistan,Turks and Caicos Islands,Tuvalu,Uganda,Ukraine,United Arab Emirates,United Kingdom of Great Britain and Northern Ireland,United States Minor Outlying Islands,United States of America,Uruguay,Uzbekistan,Vanuatu,Venezuela,Viet Nam,Virgin Islands (British),Virgin Islands (U.S),Wallis and Futuna,Western Sahara,Yemen,Zambia,Zimbabwe" delims=",">
                                                <option value="${nation}">${nation}</option>
                                            </c:forTokens>
                                        </select>                        
                                    </td>
                                </tr>	 
                                <tr><td class="content">Number ID</td><td><input name="numberID" value="${requestScope.member.numberID}" required></td></tr> 
                            </table>       

                            <div class="btnContent">
                                <button type="submit" name="action" value="Update">Save</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
