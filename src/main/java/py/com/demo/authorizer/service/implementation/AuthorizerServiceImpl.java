package py.com.demo.authorizer.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import py.com.demo.authorizer.entity.reponse.LocalidadResponse;
import py.com.demo.authorizer.exceptions.AlfaMsException;
import py.com.demo.authorizer.exceptions.AlfaMsTypeException;
import py.com.demo.authorizer.repository.AuthorizerRepository;
import py.com.demo.authorizer.client.impl.GlobalCreditClientImpl;
import py.com.demo.authorizer.entity.Response;
import py.com.demo.authorizer.entity.SystemInfoResponse;
import py.com.demo.authorizer.entity.TransactionChannelId;
import py.com.demo.authorizer.entity.request.AuthorizationRequest;
import py.com.demo.authorizer.service.AuthorizerService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class AuthorizerServiceImpl implements AuthorizerService {

    @Autowired
    private GlobalCreditClientImpl globalCreditClient;

    private final AuthorizerRepository authorizerRepository;

    private LocalidadResponse[] localidades;
    public AuthorizerServiceImpl(AuthorizerRepository authorizerRepository) {
        String url = "https://farmanet.minsal.cl/index.php/ws/getLocales";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        try {
            HttpResponse response = client.execute(request);
            String jsonResponse = EntityUtils.toString(response.getEntity());
            // Eliminar BOM
            if (jsonResponse != null && jsonResponse.startsWith("\uFEFF")) {
                jsonResponse = jsonResponse.substring(1);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            localidades = objectMapper.readValue(jsonResponse, LocalidadResponse[].class);
            System.out.println(localidades.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.authorizerRepository = authorizerRepository;        
    }

    @Override
    public SystemInfoResponse getSystemInfo(String alphaAccountNumber) throws AlfaMsException {
        try{
            return globalCreditClient.getSystemInfo();
        } catch(Exception e){
            log.error("error en getsysteminfo", e);
            throw new AlfaMsException(AlfaMsTypeException.APPLICATION, "5050", "Orden no encontrada");
        }
    }

    @Override
    public Response createAuthorization(AuthorizationRequest request) throws AlfaMsException {
        switch (TransactionChannelId.getById(request.getPhoneNumber())) {
            case BASE_1_VENTA:
                // Por ahora solo retornamos ok
                authorizerRepository.createAuthorization(request);
                break;
            default:
                // Por ahora no retornamos error a modo de prueba
                return new Response();
                // throw new AlfaMsException(AlfaMsTypeException.APPLICATION, "5051", "Orden no encontrada");
        }
        return null;
    }


    @Override
    public Response doReversion(String token, String reference) throws AlfaMsException {
        try {
            int a = 1/0;
            //authorizerRepository.createAuthorization(cardReferenceToken, request);
            return new Response();
        } catch (Exception e) {
            throw new AlfaMsException(AlfaMsTypeException.APPLICATION, "5052", "Orden no encontrada");
        }
    }

    @Override
    public List<LocalidadResponse> getLocalidad(String name, String direction, String telefono) throws AlfaMsException {
        return Arrays.stream(this.localidades)
                .filter(localidad -> localidad.getLocalidad_nombre().contains(name))
//                .filter(localidad -> localidad.getLocal_direccion().contains(direction))
                .filter(localidad -> localidad.getLocal_telefono().startsWith(telefono))
                .collect(Collectors.toList());
    }

    @Override
    public List<LocalidadResponse> getLocalidadName(String name) throws AlfaMsException {
        return Arrays.stream(this.localidades)
                .filter(localidad -> localidad.getLocalidad_nombre().contains(name))
                .collect(Collectors.toList());
    }

    @Override
    public List<LocalidadResponse> getLocalidadDireccion(String direction) throws AlfaMsException {
        return Arrays.stream(this.localidades)
                .filter(localidad -> localidad.getLocal_direccion().contains(direction))
                .collect(Collectors.toList());
    }

    @Override
    public List<LocalidadResponse> getLocalidadTelefono(String telefono) throws AlfaMsException {
        return Arrays.stream(this.localidades)
                .filter(localidad -> localidad.getLocal_telefono().startsWith(telefono))
                .collect(Collectors.toList());
    }
}
