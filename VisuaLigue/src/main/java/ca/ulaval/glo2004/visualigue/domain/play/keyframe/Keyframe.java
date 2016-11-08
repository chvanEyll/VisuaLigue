package ca.ulaval.glo2004.visualigue.domain.play.keyframe;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.domain.play.actor.Actor;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.ActorState;
import javax.xml.bind.annotation.XmlIDREF;

public class Keyframe extends DomainObject {

    private Long time;
    @XmlIDREF
    private Actor actor;
    private ActorState actorState;

    public Keyframe() {
        //Required for JAXB instanciation.
    }

    public Keyframe(Long time, Actor actor, ActorState actorState) {
        this.time = time;
        this.actor = actor;
        this.actorState = actorState;
    }

    public Long getTime() {
        return time;
    }

    public Actor getActor() {
        return actor;
    }

    public ActorState getActorState() {
        return actorState;
    }

    public ActorState mergeActorState(ActorState actorState) {
        return this.actorState.merge(actorState);
    }

    public void unmergeActorState(Actor actor, ActorState actorState) {
        this.actorState.unmerge(actorState);
    }

    public Boolean isBlank() {
        return actorState.isBlank();
    }

    public Keyframe interpolate(Double interpolant, Keyframe nextKeyframe) {
        Keyframe interpolatedKeyFrame = new Keyframe();
        interpolatedKeyFrame.time = this.time + (int) ((nextKeyframe.time - this.time) * interpolant);
        interpolatedKeyFrame.actorState = actorState.interpolate(nextKeyframe.actorState, interpolant);
        interpolatedKeyFrame.actor = actor;
        return interpolatedKeyFrame;
    }

}
