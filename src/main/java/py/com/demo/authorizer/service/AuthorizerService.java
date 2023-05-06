package py.com.demo.authorizer.service;

import py.com.demo.authorizer.entity.reponse.LocalidadResponse;
import py.com.demo.authorizer.entity.request.AuthorizationRequest;
import py.com.demo.authorizer.exceptions.AlfaMsException;
import py.com.demo.authorizer.entity.Response;
import py.com.demo.authorizer.entity.SystemInfoResponse;

import java.util.List;

public interface AuthorizerService {
    SystemInfoResponse getSystemInfo(String alphaAccount)throws AlfaMsException;

    Response createAuthorization(AuthorizationRequest request) throws AlfaMsException;

    Response doReversion(String token, String reference) throws AlfaMsException;

    List<LocalidadResponse> getLocalidad(String name, String direction, String telefono) throws AlfaMsException;

    List<LocalidadResponse> getLocalidadName(String name) throws AlfaMsException;

    List<LocalidadResponse> getLocalidadDireccion(String direction) throws AlfaMsException;

    List<LocalidadResponse> getLocalidadTelefono(String telefono) throws AlfaMsException;
}
