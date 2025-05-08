package uz.pdp.chat.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.chat.entity.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query(value = """
            select * from messages where from_user_id =:from and
                                       to_user_id =:to or from_user_id=:to
                                                             and to_user_id=:from
                                 order by date_time
          """, nativeQuery = true)
    List<Message> getChatHistory(Integer from,Integer to);


}