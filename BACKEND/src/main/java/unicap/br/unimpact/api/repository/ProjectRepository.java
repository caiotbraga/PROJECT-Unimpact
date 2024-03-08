package unicap.br.unimpact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import unicap.br.unimpact.domain.entities.Project;
import unicap.br.unimpact.domain.enums.StateEnum;
import unicap.br.unimpact.domain.enums.StatusEnum;

import java.util.List;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAllByOwnerEmail(String ownerEmail);

    List<Project> findAllByActualState_ActualStatus_Status(StatusEnum status);

    List<Project> findAllByActualState_State(StateEnum state);

    List<Project> findAllByActualState_StateAndActualState_ActualStatus_Status(StateEnum state, StatusEnum status);

    @Query(nativeQuery = true, value = "select * from project p " +
            "join responsible r on r.id = p.responsible_id " +
            "join physical_person pp on pp.id = r.physical_person_responsible_id  " +
            "where pp.email =:responsibleEmail and p.is_active = true")
    List<Project> findAllByRepresentativeEmail(String responsibleEmail);

    @Query(nativeQuery = true, value = "select * from project p " +
            "join responsible r on r.id = p.responsible_id " +
            "join responsible_groups_responsibles rg on rg.responsible_id = r.id " +
            "join groups g on g.id = rg.groups_responsibles_id " +
            "join groups_members gm on gm.group_id = g.id " +
            "join physical_person pp on pp.id = gm.members_id " +
            "where pp.email =:responsibleEmail and p.is_active = true")
    List<Project> findAllByRepresentativeEmailInGroup(String responsibleEmail);

    @Query(nativeQuery = true, value = "select distinct * from project p " +
            "join state_project state on state.id = p.actual_state_id " +
            "join status_project status on status.id = state.actual_status_id " +
            "join physical_person pp on pp.id = status.middle_employee_id  " +
            "where pp.email =:email and state.state <> 'DRAFT' " +
            "or (state.state = 'DRAFT' and status.status <> 'IN_ELABORATION')")
    List<Project> findAllByMiddleEmployee(String email);

    @Query(nativeQuery = true, value = "select distinct * from project p " +
            "join state_project state on state.id = p.actual_state_id " +
            "join status_project status on status.id = state.actual_status_id " +
            "join status_project_departments sdp on sdp.status_project_id = status.id " +
            "join groups g on g.id = sdp.departments_id " +
            "join groups_members gm on gm.group_id = g.id " +
            "join physical_person pp on pp.id = gm.members_id " +
            "where pp.email =:participateEmail and state.state <> 'DRAFT' " +
            "or (state.state = 'DRAFT' and status.status <> 'IN_ELABORATION')")
    List<Project> findAllByDepartmentParticipate(String participateEmail);

    @Query(nativeQuery = true, value = "select distinct * from project p " +
            "join state_project state on state.id = p.actual_state_id " +
            "join status_project status on status.id = state.actual_status_id " +
            "join status_project_departments sdp on sdp.status_project_id = status.id " +
            "join groups g on g.id = sdp.departments_id " +
            "join groups_members gm on gm.group_id = g.id " +
            "join physical_person pp on pp.id = gm.members_id " +
            "where pp.email =:participateEmail and state.state <> 'DRAFT' " +
            "and status.status <> 'FINISHED'")
    List<Project> findAllByDepartmentParticipateInDevelopment(String participateEmail);

    @Query(nativeQuery = true, value = "select * from project p " +
            "join responsible r on r.id = p.responsible_id " +
            "join responsible_groups_responsibles rg on rg.responsible_id = r.id " +
            "join groups g on g.id = rg.groups_responsibles_id " +
            "where g.id =:groupId")
    List<Project> findAllByGroupId(Long groupId);

    @Query(nativeQuery = true, value = "select * from project p " +
            "join state_project state on state.id = p.actual_state_id " +
            "join status_project status on status.id = state.actual_status_id " +
            "join status_project_departments sdp on sdp.status_project_id = status.id " +
            "join groups g on g.id = sdp.departments_id " +
            "where g.id =:departmentId and state.state <> 'DRAFT' " +
            "or (state.state = 'DRAFT' and status.status <> 'IN_ELABORATION')")
    List<Project> findAllByDepartmentId(Long departmentId);

    @Query(nativeQuery = true, value = "select distinct * from project p " +
            "join state_project state on state.id = p.actual_state_id " +
            "join status_project status on status.id = state.actual_status_id " +
            "where state.state <> 'DRAFT' or (state.state = 'DRAFT' " +
            "and status.status <> 'IN_ELABORATION')")
    List<Project> findAllProjects();
}
