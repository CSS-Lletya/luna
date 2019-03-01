package io.luna.game.event;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
/**
 * An Object passed through a pipeline to be intercepted by event listeners. Events should
 * <strong>always</strong> be immutable to ensure that they cannot be modified while being passed
 * through the pipeline.
 *
 * @author lare96 <http://github.org/lare96>
 */
public class Event {

    /**
     * The asynchronous logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger();

    /**
     * The pipeline this event is passing through.
     */
    private Optional<EventListenerPipeline> pipeline = Optional.empty();

    /**
     * @return The pipeline this event is passing through.
     */
    public Optional<EventListenerPipeline> pipeline() {
        return pipeline;
    }

    /**
     * Sets a new pipeline instance. This is used to terminate traversal of the event.
     *
     * @param pipeline The next pipeline to traverse.
     */
    public void pipeline(EventListenerPipeline pipeline) {
        this.pipeline = Optional.ofNullable(pipeline);
    }
}
