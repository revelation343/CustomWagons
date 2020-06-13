package com.revelation.CustomWagons;

import com.revelation.CustomWagons.util.SeatsFacadeImpl;
import com.revelation.CustomWagons.util.VehicleFacadeImpl;
import com.wurmonline.server.Features;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.behaviours.Seat;
import com.wurmonline.server.behaviours.Vehicle;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import javassist.CtClass;
import javassist.CtPrimitiveType;
import javassist.NotFoundException;
import javassist.bytecode.Descriptor;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.classhooks.InvocationHandlerFactory;
import org.gotti.wurmunlimited.modloader.interfaces.Configurable;
import org.gotti.wurmunlimited.modloader.interfaces.ItemTemplatesCreatedListener;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Initiator implements WurmServerMod, ItemTemplatesCreatedListener, Configurable {

    public static boolean enableWagoner = false;
    public static boolean enableCanvaslessWagon = false;
    public static int wagonerItemID = 6000;
    public static int canvaslessWagonID = 6001;

    public String getVersion() {
        return "v1.1";
    }

    public void configure(Properties properties) {
        enableWagoner = Boolean.parseBoolean(properties.getProperty("enableWagoner"));
        enableCanvaslessWagon = Boolean.parseBoolean(properties.getProperty("enableCanvaslessWagon"));
        wagonerItemID = Integer.parseInt(properties.getProperty("wagonerItemID"));
        canvaslessWagonID = Integer.parseInt(properties.getProperty("canvaslessWagonID"));

    }

    public void onItemTemplatesCreated() {
    registerPermissionsHook();
    registerWagonHook();
    new CustomWagons();
    logInfo("Initializing Custom Wagons");

    }
        public void registerWagonHook() {
            try {
                CtClass[] input = {
                        HookManager.getInstance().getClassPool().get("com.wurmonline.server.items.Item"),
                        HookManager.getInstance().getClassPool().get("com.wurmonline.server.behaviours.Vehicle")
                };
                CtClass output = CtPrimitiveType.voidType;
                HookManager.getInstance().registerHook("com.wurmonline.server.behaviours.Vehicles", "setSettingsForVehicle",
                        Descriptor.ofMethod(output, input), () -> (proxy, method, args) -> {
                            Initiator.logInfo("Adding vehicle configuration for Custom Wagons");
                            Item item = (Item) args[0];
                            int itemId = item.getTemplateId();
                            if (itemId == wagonerItemID) {
                                Vehicle vehicle = (Vehicle) args[1];
                                VehicleFacadeImpl vehfacade = new VehicleFacadeImpl(vehicle);
                                if (Features.Feature.WAGON_PASSENGER.isEnabled()) {
                                    vehfacade.createPassengerSeats(1);
                                } else {
                                    vehfacade.createPassengerSeats(0);
                                }
                                vehfacade.setPilotName("driver");
                                vehfacade.setCreature(false);
                                vehfacade.setEmbarkString("ride");
                                vehfacade.setEmbarksString("rides");
                                vehicle.name = item.getName();
                                vehicle.setSeatFightMod(0, 0.9f, 0.3f);
                                vehicle.setSeatOffset(0, 0.0f, 0.0f, 0.0f, 1.453f);
                                if (Features.Feature.WAGON_PASSENGER.isEnabled()) {
                                    vehicle.setSeatFightMod(1, 1.0f, 0.4f);
                                    vehicle.setSeatOffset(1, 4.05f, 0.0f, 0.84f);
                                }
                                vehicle.maxHeightDiff = 0.07f;
                                vehicle.maxDepth = -0.07f;
                                vehicle.skillNeeded = 21.0f;
                                vehfacade.setMaxSpeed(1.0f);
                                vehicle.commandType = 2;
                                SeatsFacadeImpl seatfacad = new SeatsFacadeImpl();
                                final Seat[] hitches = {seatfacad.CreateSeat((byte) 2), seatfacad.CreateSeat((byte) 2), seatfacad.CreateSeat((byte) 2), seatfacad.CreateSeat((byte) 2)};
                                hitches[0].offx = -2.0f;
                                hitches[0].offy = -1.0f;
                                hitches[1].offx = -2.0f;
                                hitches[1].offy = 1.0f;
                                hitches[2].offx = -5.0f;
                                hitches[2].offy = -1.0f;
                                hitches[3].offx = -5.0f;
                                hitches[3].offy = 1.0f;
                                vehicle.addHitchSeats(hitches);
                                vehicle.setMaxAllowedLoadDistance(4);
                                return null;
                            }
                            return method.invoke(proxy, args);
                        });

                HookManager.getInstance().registerHook("com.wurmonline.server.behaviours.Vehicles", "setSettingsForVehicle",
                        Descriptor.ofMethod(output, input), () -> (proxy, method, args) -> {
                            Initiator.logInfo("Adding vehicle configuration for Custom Wagons");
                            Item item = (Item) args[0];
                            int itemId = item.getTemplateId();
                            if (itemId == canvaslessWagonID) {
                                Vehicle vehicle = (Vehicle) args[1];
                                VehicleFacadeImpl vehfacade = new VehicleFacadeImpl(vehicle);
                                if (Features.Feature.WAGON_PASSENGER.isEnabled()) {
                                    vehfacade.createPassengerSeats(1);
                                } else {
                                    vehfacade.createPassengerSeats(0);
                                }
                                vehfacade.setPilotName("driver");
                                vehfacade.setCreature(false);
                                vehfacade.setEmbarkString("ride");
                                vehfacade.setEmbarksString("rides");
                                vehicle.name = item.getName();
                                vehicle.setSeatFightMod(0, 0.9f, 0.3f);
                                vehicle.setSeatOffset(0, 0.0f, 0.0f, 0.0f, 1.453f);
                                if (Features.Feature.WAGON_PASSENGER.isEnabled()) {
                                    vehicle.setSeatFightMod(1, 1.0f, 0.4f);
                                    vehicle.setSeatOffset(1, 4.05f, 0.0f, 0.84f);
                                }
                                vehicle.maxHeightDiff = 0.07f;
                                vehicle.maxDepth = -0.07f;
                                vehicle.skillNeeded = 21.0f;
                                vehfacade.setMaxSpeed(1.0f);
                                vehicle.commandType = 2;
                                SeatsFacadeImpl seatfacad = new SeatsFacadeImpl();
                                final Seat[] hitches = {seatfacad.CreateSeat((byte) 2), seatfacad.CreateSeat((byte) 2), seatfacad.CreateSeat((byte) 2), seatfacad.CreateSeat((byte) 2)};
                                hitches[0].offx = -2.0f;
                                hitches[0].offy = -1.0f;
                                hitches[1].offx = -2.0f;
                                hitches[1].offy = 1.0f;
                                hitches[2].offx = -5.0f;
                                hitches[2].offy = -1.0f;
                                hitches[3].offx = -5.0f;
                                hitches[3].offy = 1.0f;
                                vehicle.addHitchSeats(hitches);
                                vehicle.setMaxAllowedLoadDistance(4);
                                return null;
                            }
                            return method.invoke(proxy, args);
                        });
            } catch (NotFoundException e) {
                Initiator.logInfo("CustomWagonHook: " + e.toString());
            }
        }

    public void registerPermissionsHook(){
        try {
            CtClass[] input = {
                    HookManager.getInstance().getClassPool().get("com.wurmonline.server.creatures.Creature"),
                    HookManager.getInstance().getClassPool().get("com.wurmonline.server.items.Item")

            };
            CtClass output = HookManager.getInstance().getClassPool().get("java.util.List");

            HookManager.getInstance().registerHook("com.wurmonline.server.behaviours.VehicleBehaviour", "getVehicleBehaviours",
                    Descriptor.ofMethod(output, input), new InvocationHandlerFactory() {
                        @Override
                        public InvocationHandler createInvocationHandler() {
                            return new InvocationHandler() {
                                @Override
                                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                    @SuppressWarnings("unchecked")
                                    List<ActionEntry> original = (List<ActionEntry>) method.invoke(proxy, args);
                                    Item item = (Item) args[1];
                                    Creature performer = (Creature) args[0];
                                    LinkedList<ActionEntry> permissions = new LinkedList<ActionEntry>();
                                    if (item.mayManage(performer)) {
                                        int itemId = item.getTemplateId();
                                        if (itemId == wagonerItemID) {
                                            permissions.add(new ActionEntry((short)669, "Manage Custom Wagons", "viewing"));
                                        }
                                    }
                                    if (item.maySeeHistory(performer)) {
                                        int itemId = item.getTemplateId();
                                        if (itemId == wagonerItemID) {
                                            permissions.add(new ActionEntry((short)691, "History of Custom Wagons", "viewing"));
                                        }
                                    }
                                    if (!permissions.isEmpty()) {
                                        if (permissions.size() > 1) {
                                            Collections.sort(permissions);
                                            original.add(new ActionEntry((short)(- permissions.size()), "Permissions", "viewing"));
                                        }
                                        original.addAll(permissions);
                                    }
                                    return original;
                                }

                            };
                        }
                    });
            HookManager.getInstance().registerHook("com.wurmonline.server.behaviours.VehicleBehaviour", "getVehicleBehaviours",
                    Descriptor.ofMethod(output, input), new InvocationHandlerFactory() {
                        @Override
                        public InvocationHandler createInvocationHandler() {
                            return new InvocationHandler() {
                                @Override
                                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                    @SuppressWarnings("unchecked")
                                    List<ActionEntry> original = (List<ActionEntry>) method.invoke(proxy, args);
                                    Item item = (Item) args[1];
                                    Creature performer = (Creature) args[0];
                                    LinkedList<ActionEntry> permissions = new LinkedList<ActionEntry>();
                                    if (item.mayManage(performer)) {
                                        int itemId = item.getTemplateId();
                                        if (itemId == canvaslessWagonID) {
                                            permissions.add(new ActionEntry((short)669, "Manage Custom Wagons", "viewing"));
                                        }
                                    }
                                    if (item.maySeeHistory(performer)) {
                                        int itemId = item.getTemplateId();
                                        if (itemId == canvaslessWagonID) {
                                            permissions.add(new ActionEntry((short)691, "History of Custom Wagons", "viewing"));
                                        }
                                    }
                                    if (!permissions.isEmpty()) {
                                        if (permissions.size() > 1) {
                                            Collections.sort(permissions);
                                            original.add(new ActionEntry((short)(- permissions.size()), "Permissions", "viewing"));
                                        }
                                        original.addAll(permissions);
                                    }
                                    return original;
                                }

                            };
                        }
                    });
        }
        catch (Exception e) {
            Initiator.logWarning("Permission hook: " + e.toString());
        }
    }


    public static Logger logger = Logger.getLogger(Initiator.class.getName());
    public static void logException(String msg, Throwable e) {
        if (logger != null)
            logger.log(Level.SEVERE, msg, e);
    }
    public static void logWarning(String msg) {
        if (logger != null)
            logger.log(Level.WARNING, msg);
    }
    public static void logInfo(String msg) {
        if (logger != null)
            logger.log(Level.INFO, msg);
    }
}
