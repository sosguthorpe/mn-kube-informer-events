package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.kubernetes.client.openapi.ApiClient;
import io.micronaut.context.event.BeanCreatedEvent;
import io.micronaut.context.event.BeanCreatedEventListener;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Singleton;

@MicronautTest
public class EventListenerTest {
	
		static final Logger log = LoggerFactory.getLogger(EventListenerTest.class);

		
		static boolean eventFired = false;
		
		
		@BeforeEach
		void resetFiredFlag() {
			eventFired = false;
		}

		@Test
		void listenerShouldNotFire() {
				assertFalse(eventFired);
		}
		
		
		@Test
		void listenerShouldFire(ApiClient apiClient) {
				assertTrue(eventFired);
		}
}

@Singleton
class K8sClientEventListener implements BeanCreatedEventListener<ApiClient> {

		@Override
		public ApiClient onCreated(BeanCreatedEvent<ApiClient> event) {
				EventListenerTest.log.info("Running Client customization");
				EventListenerTest.eventFired = true;
				return event.getBean();
		}
}
