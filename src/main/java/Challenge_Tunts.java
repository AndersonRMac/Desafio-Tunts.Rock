import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.Strings;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Challenge_Tunts {
    private static Sheets sheetsService;
    private static String APLICATION_NAME = "Challenge Tunts";
    private static String SPREADSHEET_ID = "11rMp38TrBTcfUVK6IaeO161fYEP-Kag7eA4woyJaG18";

    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();


    private static Credential authorize() throws IOException, GeneralSecurityException{
        InputStream in = Challenge_Tunts.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
            if (in == null){
                System.out.println("File not found -> " + CREDENTIALS_FILE_PATH);
            }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                JSON_FACTORY, new InputStreamReader(in)
        );

        List<String>  SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS);

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),JSON_FACTORY, clientSecrets,SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
                .setAccessType("offline")
                .build();

        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver())
                .authorize("user");
        return credential;
    }


    public static Sheets getSheetsService() throws IOException, GeneralSecurityException{
        Credential credential = authorize();
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),JSON_FACTORY,credential)
                .setApplicationName(APLICATION_NAME)
                .build();
    }

    public static void main(String[] args) throws IOException, GeneralSecurityException  {
        sheetsService = getSheetsService();
        String range = "engenharia_de_software!A4:F27";

         ValueRange appendBody = new ValueRange();


    }
}



//    ValueRange response = sheetsService.spreadsheets().values()
//            .get(SPREADSHEET_ID, range)
//            .execute();
//
//    List<List<Object>> values = response.getValues();
//
//        if (values == null || values.isEmpty()){
//                System.out.println("No Data Found!");
//                }else{
//                for (List row : values){
//                System.out.printf("%s %s from %s\n" , row.get(0), row.get(1),row.get(2));
//                }
//                }


//.setValues(Arrays.asList(Arrays.asList("Média")));
//
//        AppendValuesResponse appendResults = sheetsService.spreadsheets().values()
//        .append(SPREADSHEET_ID,"engenharia_de_software", appendBody)
//        .setValueInputOption("USER_ENTERED")
//        .setInsertDataOption("INSERT_ROWS")
//        .setIncludeValuesInResponse(true)
//        .execute();