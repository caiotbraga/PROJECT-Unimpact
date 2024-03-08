package unicap.br.unimpact.service.services.group;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicap.br.unimpact.api.dtos.response.GroupResponse;
import unicap.br.unimpact.domain.entities.Group;
import unicap.br.unimpact.repository.GroupRepository;
import unicap.br.unimpact.service.interfaces.GenericCRUD;
import unicap.br.unimpact.service.services.others.AuthService;
import unicap.br.unimpact.service.services.person.physical.PhysicalPersonService;

import javax.annotation.PostConstruct;
import java.util.List;


@Service
@Slf4j
@AllArgsConstructor
public class GroupService extends GenericCRUD<Group, GroupResponse> {

    @Autowired
    GroupRepository repository;

    @Autowired
    GroupPreCondition preCondition;

    @Autowired
    PhysicalPersonService physicalPersonService;

    @Autowired
    AuthService authService;

    @Autowired
    private GroupRegister register;

    @Autowired
    private GroupRemover remover;

    @Autowired
    private GroupSearcher searcher;

    @PostConstruct
    public void init() {
        this.init(Group.class, GroupResponse.class, this.repository, this.preCondition);
    }


    public void registerNewMember(Group group, String recipient) {
        register.registerNewMember(group, recipient);
    }

    public void removeMember(Long groupId, String recipient) {
        remover.removeMember(groupId, recipient);
    }


    public boolean memberInGroup(Group group, String memberEmail) {
        return searcher.memberInGroup(group, memberEmail);
    }


    public boolean memberInGroupList(List<Group> groups, String memberEmail) {
        return searcher.memberInGroupList(groups, memberEmail);
    }


    public List<Group> getAllGroupsByUserOwner(String ownerUserEmail) {
        return searcher.getAllGroupsByUserOwner(ownerUserEmail);
    }


    public List<Group> getAllGroupsByUserParticipate(String participateUserEmail) {
        return searcher.getAllGroupsByUserParticipate(participateUserEmail);
    }


    public List<Group> getAllDepartmentsOfUnicap() {
        return searcher.getAllDepartmentsOfUnicap();
    }


    public Group getGroupByUserOwnerAndName(String ownerUserEmail, String groupName) {
        return searcher.getGroupByUserOwnerAndName(ownerUserEmail, groupName);
    }

}
