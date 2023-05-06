package py.com.demo.authorizer.repository;

import py.com.demo.authorizer.exceptions.AlfaMsException;
import py.com.demo.authorizer.entity.request.AuthorizationRequest;

public interface AuthorizerRepository {

    String createAuthorization(AuthorizationRequest request) throws AlfaMsException;
}
