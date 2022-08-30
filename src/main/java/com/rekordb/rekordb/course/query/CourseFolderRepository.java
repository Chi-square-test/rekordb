package com.rekordb.rekordb.course.query;

import com.rekordb.rekordb.course.CourseFolder;
import com.rekordb.rekordb.course.CourseId;
import com.rekordb.rekordb.course.FolderId;
import com.rekordb.rekordb.user.domain.userInfo.UserId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CourseFolderRepository extends MongoRepository<CourseFolder, CourseId> {
    Optional<CourseFolder> findByUserIdAndRootFolderTrue(UserId userId);
    Optional<CourseFolder> findByUserIdAndFolderId(UserId userId, FolderId folderId);

}
