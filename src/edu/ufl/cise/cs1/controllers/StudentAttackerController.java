package edu.ufl.cise.cs1.controllers;
import game.controllers.AttackerController;
import game.models.*;
import java.awt.*;
import java.util.List;

public final class StudentAttackerController implements AttackerController {
    public void init(Game game) {
    }
    public void shutdown(Game game) {
    }
    public int update(Game game,long timeDue){
        int nextDir;

        Attacker gator=game.getAttacker();
        List<Node> pills=game.getPillList();
        List<Node> powerPills=game.getPowerPillList();
        Actor closestDefender;
        Node node_=gator.getLocation();
        List<Defender> theDefenders=game.getDefenders();
        Defender closestDefender_=theDefenders.get(0);
        Node currentLocation;
        Node closestPill=gator.getTargetNode(pills,true);


        //Gets the location of the closest defender as a defender object rather than an actor object
        currentLocation=theDefenders.get(0).getLocation();
        for(int i=0;i<theDefenders.size()-1;i++){
            Node location2=theDefenders.get(i+1).getLocation();
            if(node_.getPathDistance(currentLocation)>node_.getPathDistance(location2)){
                currentLocation=location2;
            }
        }
        if(currentLocation==theDefenders.get(0).getLocation()){
            closestDefender_=theDefenders.get(0);
        }
        if(currentLocation==theDefenders.get(1).getLocation()){
            closestDefender_=theDefenders.get(1);
        }
        if(currentLocation==theDefenders.get(2).getLocation()){
            closestDefender_=theDefenders.get(2);
        }
        if(currentLocation==theDefenders.get(3).getLocation()){
            closestDefender_=theDefenders.get(3);
        }

        closestDefender=gator.getTargetActor(theDefenders,true); //gets closest defender as an actor

        if(powerPills.size()==0){//if there are no power pills left then go for the closest pills
            if(closestDefender_.isVulnerable()){
                nextDir=gator.getNextDir(closestDefender.getLocation(),true);
            }else{
                for(int i = 0; i < theDefenders.size(); i++){
                    if(theDefenders.get(i).isVulnerable()){
                        nextDir = gator.getNextDir(theDefenders.get(i).getLocation(), true);
                    }
                }
                if(theDefenders.get(0).isVulnerable()){
                    nextDir = gator.getNextDir(theDefenders.get(0).getLocation(), true);
                }
                else if(theDefenders.get(1).isVulnerable()){
                    nextDir = gator.getNextDir(theDefenders.get(1).getLocation(), true);
                }
                else if(theDefenders.get(2).isVulnerable()){
                    nextDir = gator.getNextDir(theDefenders.get(2).getLocation(), true);
                }
                else if(theDefenders.get(3).isVulnerable()){
                    nextDir = gator.getNextDir(theDefenders.get(3).getLocation(), true);
                }
                else{
                nextDir=gator.getNextDir(closestPill,true); }
            }
            return nextDir;

        }else{
            Node closestPowerPill=gator.getTargetNode(powerPills,true);

            if(closestDefender_.isVulnerable()){//attack closest defender if vulnerable
                nextDir=gator.getNextDir(closestDefender.getLocation(),true);
                return nextDir;

            }else{//Otherwise, if the defender is close than the power pill, then go for the power pill, if the power pill
                //is closest then that means the defender is far away and it should go for the pills instead.
                if(node_.getPathDistance(closestDefender.getLocation())<node_.getPathDistance(closestPowerPill)){
                    nextDir=gator.getNextDir(closestPowerPill,true);
                    return nextDir;
                }
                else if(node_.getPathDistance(closestDefender.getLocation()) > node_.getPathDistance(closestPowerPill) && node_.getPathDistance(closestDefender.getLocation()) < 4){
                    nextDir = gator.getNextDir(closestPowerPill, true);
                }
                else if(node_.getPathDistance(closestDefender.getLocation()) == node_.getPathDistance(closestPowerPill)){
                    nextDir = gator.getNextDir(closestPowerPill, true);
                }

                    else{
                    if((node_.getPathDistance(closestDefender.getLocation())>=9)&&node_.getPathDistance(closestPowerPill)<=4){
                        nextDir = gator.getNextDir(closestPowerPill, false);
                        return nextDir;
                    }
                    else{
                        nextDir=gator.getNextDir(closestPill,true);


                    }
                }
            }
        }
gator.addPathTo(game, Color.DARK_GRAY, closestDefender.getLocation());

        return nextDir;

    }
}
