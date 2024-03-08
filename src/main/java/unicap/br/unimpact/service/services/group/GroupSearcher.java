package unicap.br.unimpact.service.services.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.domain.entities.Group;
import unicap.br.unimpact.domain.entities.PhysicalPerson;
import unicap.br.unimpact.repository.GroupRepository;
import unicap.br.unimpact.service.exceptions.business.NotFoundException;
import unicap.br.unimpact.service.services.others.AuthService;
import unicap.br.unimpact.service.services.person.physical.PhysicalPersonService;

import java.util.List;
import java.util.Objects;

@Component
public class GroupSearcher {

    @Autowired
    GroupRepository repository;

    @Autowired
    PhysicalPersonService physicalPersonService;

    @Autowired
    AuthService authService;

    @Value("${unicap.email}")
    private String universityAccount;


    public boolean memberInGroup(Group group, String memberEmail) {
        boolean contains = false;
        for (PhysicalPerson member : group.getMembers()) {
            if (member.getEmail().equals(memberEmail)) {
                contains = true;
                break;
            }
        }
        return contains;
    }


    public boolean memberInGroupList(List<Group> groups, String memberEmail) {
        boolean contains = false;
        for (Group group : groups) {
            if (this.memberInGroup(group, memberEmail)) {
                contains = true;
                break;
            }
        }
        return contains;
    }


    public List<Group> getAllGroupsByUserOwner(String ownerUserEmail) {
        return repository.findAllByOwnerUserEmail(ownerUserEmail);
    }


    public List<Group> getAllGroupsByUserParticipate(String participateUserEmail) {
        return repository.findAllByParticipateUser(participateUserEmail);
    }


    public List<Group> getAllDepartmentsOfUnicap() {
        return repository.findAllDepartmentsByUniversity(universityAccount);
    }


    public Group getGroupByUserOwnerAndName(String ownerUserEmail, String groupName) {
        List<Group> groups = this.getAllGroupsByUserOwner(ownerUserEmail);

        Group groupResponse = null;

        for (Group group : groups) {
            if (group.getName().equals(groupName)) {
                groupResponse = group;
                break;
            }
        }

        if (Objects.isNull(groupResponse))
            throw new NotFoundException(Group.class);

        return groupResponse;
    }

}
