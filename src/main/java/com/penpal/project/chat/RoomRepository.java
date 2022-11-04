package com.penpal.project.chat;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends JpaRepository<Room, Integer>{
	@Query(   "select distinct r "
			+ "from Room r "
			+ "where"
			+ "	   (r.maker.id = :maker_id and "
			+ "	   r.guest.id = :guest_id) or "
			+ "	   (r.maker.id = :guest_id and "
			+ "	   r.guest.id = :maker_id)"
			)
	Optional<Room> findByRoom(@Param("maker_id") Integer makerId, @Param("guest_id") Integer GuestId);
}
