package com.ecsolutions.cadastros.service;


import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class KeycloakGroupsService {

    private final UsersResource usersResource;

    public void addGroupsById(String keycloakId, Collection<String> groupIds) {
        var userResource = this.usersResource.get(keycloakId);
        var actualGroups = userResource.groups();
        actualGroups.stream()
                .filter(actual -> groupIds.stream().noneMatch(groupId -> Objects.equals(actual.getId(), groupId)))
                .forEach(toRemove -> userResource.leaveGroup(toRemove.getId()));

        groupIds.stream()
                .filter(groupId -> actualGroups.stream().noneMatch(actual -> Objects.equals(groupId, actual.getId())))
                .forEach(userResource::joinGroup);
    }


}
