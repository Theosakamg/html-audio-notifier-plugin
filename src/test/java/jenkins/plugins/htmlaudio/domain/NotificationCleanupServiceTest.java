package jenkins.plugins.htmlaudio.domain;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static support.DomainObjectFactory.*;

import java.util.ArrayList;
import java.util.List;
import jenkins.plugins.htmlaudio.domain.impl.DefaultNotificationCleanupService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import support.NotificationRepositoryAdapter;


@RunWith(JUnit4.class)
public class NotificationCleanupServiceTest {
    
    private final List<Notification> notifications = new ArrayList<Notification>();
    
    private final DefaultNotificationCleanupService svc = new DefaultNotificationCleanupService();
    
    
    {
        svc.setNotificationRepository(new NotificationRepositoryAdapter() {
            @Override public void remove(NotificationRemover remover) {
                remover.remove(notifications.iterator());
            }
        });
    }
    
    
    @Test
    public void notifications_of_expected_age_are_removed_from_repository() {
        final long second = 1000;
        
        final Notification n1 = createNotificationOfCertainAge(120 * second);
        final Notification n2 = createNotificationOfCertainAge(61 * second);
        final Notification n3 = createNotificationOfCertainAge(59 * second);
        final Notification n4 = createNotificationOfCertainAge(50 * second);
        
        notifications.addAll(asList(n1, n2, n3, n4));
        
        // everything older than 1 minute should be removed
        svc.removeExpired();
        assertEquals(asList(n3, n4), notifications);
    }
}
