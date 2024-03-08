package unicap.br.unimpact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import unicap.br.unimpact.domain.entities.Group;

import java.util.List;


@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findAllByOwnerUserEmail(String ownerUser);

    boolean existsByOwnerUserEmailAndName(String ownerUser, String name);

    @Query(nativeQuery = true, value = "select distinct * from groups g " +
            "join groups_members gp on gp.group_id = g.id " +
            "join physical_person p on p.id = gp.members_id " +
            "where p.email =:ownerUserEmail")
    List<Group> findAllByParticipateUser(String ownerUserEmail);

    @Query(nativeQuery = true, value = "select distinct * from groups g " +
            "where g.owner_user_email =:loginUniversity")
    List<Group> findAllDepartmentsByUniversity(String loginUniversity);
}
