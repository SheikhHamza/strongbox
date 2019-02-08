package org.carlspring.strongbox.services.impl;

import org.carlspring.strongbox.StorageApiTestConfig;
import org.carlspring.strongbox.data.CacheManagerTestExecutionListener;
import org.carlspring.strongbox.domain.RepositoryArtifactIdGroupEntry;
import org.carlspring.strongbox.services.RepositoryArtifactIdGroupService;

import javax.inject.Inject;

import com.orientechnologies.orient.core.storage.ORecordDuplicatedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Przemyslaw Fusik
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "test")
@ContextConfiguration(classes = StorageApiTestConfig.class)
@TestExecutionListeners(listeners = { CacheManagerTestExecutionListener.class }, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
class RepositoryArtifactIdGroupServiceImplTest
{

    @Inject
    RepositoryArtifactIdGroupService repositoryArtifactIdGroupService;

    @Test
    public void repositoryArtifactIdGroupShouldBeProtectedByIndex()
    {
        Assertions.assertThrows(ORecordDuplicatedException.class, () -> {
            RepositoryArtifactIdGroupEntry g1 = new RepositoryArtifactIdGroupEntry();
            g1.setName("strongbox-art");
            g1.setRepositoryId("strongbox-rId");
            g1.setStorageId("strongbox-sId");
            repositoryArtifactIdGroupService.save(g1);

            RepositoryArtifactIdGroupEntry g2 = new RepositoryArtifactIdGroupEntry();
            g2.setName("strongbox-art");
            g2.setRepositoryId("strongbox-rId");
            g2.setStorageId("strongbox-sId");
            repositoryArtifactIdGroupService.save(g2);
        });
    }
}