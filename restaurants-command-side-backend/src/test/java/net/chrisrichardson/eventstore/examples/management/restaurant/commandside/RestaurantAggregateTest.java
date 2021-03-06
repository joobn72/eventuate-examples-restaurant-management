package net.chrisrichardson.eventstore.examples.management.restaurant.commandside;

import net.chrisrichardson.eventstore.CommandProcessingAggregates;
import net.chrisrichardson.eventstore.Event;
import net.chrisrichardson.eventstore.examples.management.restaurant.common.RestaurantInfo;
import net.chrisrichardson.eventstore.examples.management.restaurant.common.event.RestaurantCreatedEvent;
import net.chrisrichardson.eventstore.examples.management.restaurant.common.event.RestaurantUpdatedEvent;
import net.chrisrichardson.eventstore.examples.management.restaurant.testutil.RestaurantMother;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class RestaurantAggregateTest {

  @Test
  public void testRestaurantCommands() {
    RestaurantAggregate restaurantAggregate = new RestaurantAggregate();
    RestaurantInfo restaurantInfo = RestaurantMother.makeRestaurant();

    List<Event> events = CommandProcessingAggregates.processToList(restaurantAggregate, (RestaurantCommand)new CreateRestaurantCommand(restaurantInfo));

    Assert.assertEquals(1, events.size());
    Assert.assertEquals(RestaurantCreatedEvent.class, events.get(0).getClass());

    restaurantAggregate.applyEvent(events.get(0));
    Assert.assertEquals(restaurantInfo, restaurantAggregate.getRestaurant());

    restaurantInfo.setType("cantina");
    events = CommandProcessingAggregates.processToList(restaurantAggregate, (RestaurantCommand)new UpdateRestaurantCommand(restaurantInfo));

    Assert.assertEquals(1, events.size());
    Assert.assertEquals(RestaurantUpdatedEvent.class, events.get(0).getClass());

    restaurantAggregate.applyEvent(events.get(0));
    Assert.assertEquals(restaurantInfo, restaurantAggregate.getRestaurant());
  }
}
