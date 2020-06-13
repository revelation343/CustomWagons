package com.revelation.CustomWagons;

import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.items.*;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import java.io.IOException;

public class CustomWagons implements WurmServerMod, ItemTypes{

    public CustomWagons() {
        Initiator.logInfo("Creating Custom Wagons");
        try {
            ItemTemplateCreator.createItemTemplate(Initiator.wagonerItemID, 3, "Wagoner wagon", "Wagoner wagons", "almost full", "somewhat occupied", "half-full", "emptyish", "A fairly large wagon designed to be dragged by four animals.", new short[]{108, 1, 31, 21, 51, 52, 44, 117, 193, 134, 47, 48, 176, 180, 160, 54}, (short) 60, (short) 41, 0, 9072000L, 550, 300, 410, -10, MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.transports.medium.wagon.wagoner.unloaded.", 70.0F, 240000, (byte) 14, 50000, true, 0);
            ItemTemplateCreator.createItemTemplate(Initiator.canvaslessWagonID, 3, "Canvasless wagon", "Canvasless wagons", "almost full", "somewhat occupied", "half-full", "emptyish", "A fairly large wagon designed to be dragged by four animals.", new short[]{108, 1, 31, 21, 51, 52, 44, 117, 193, 134, 47, 48, 176, 180, 160, 54}, (short) 60, (short) 41, 0, 9072000L, 550, 300, 410, -10, MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY, "model.transports.medium.wagon.canvasless.", 70.0F, 240000, (byte) 14, 50000, true, 0);
        } catch (IOException var2) {
            var2.printStackTrace();
        }

        if(Initiator.enableWagoner) {
            AdvancedCreationEntry wagoner = CreationEntryCreator.createAdvancedEntry(10044, 22, 191, Initiator.wagonerItemID, false, false, 0.0f, true, true, 0, 40.0, CreationCategories.CARTS);
            wagoner.addRequirement(new CreationRequirement(1, 191, 1, true));
            wagoner.addRequirement(new CreationRequirement(2, 22, 20, true));
            wagoner.addRequirement(new CreationRequirement(3, 23, 4, true));
            wagoner.addRequirement(new CreationRequirement(4, 218, 10, true));
            wagoner.addRequirement(new CreationRequirement(5, 632, 2, true));
            wagoner.addRequirement(new CreationRequirement(6, 486, 2, true));
            Initiator.logInfo("Added creation entry for wagoner with templateID " + Initiator.wagonerItemID);
        }

        if (Initiator.enableCanvaslessWagon){
            AdvancedCreationEntry canvaslesswagon = CreationEntryCreator.createAdvancedEntry(10044, 22, 191, Initiator.canvaslessWagonID, false, false, 0.0f, true, true, 0, 40.0, CreationCategories.CARTS);
            canvaslesswagon.addRequirement(new CreationRequirement(1, 191, 1, true));
            canvaslesswagon.addRequirement(new CreationRequirement(2, 22, 20, true));
            canvaslesswagon.addRequirement(new CreationRequirement(3, 23, 4, true));
            canvaslesswagon.addRequirement(new CreationRequirement(4, 218, 10, true));
            canvaslesswagon.addRequirement(new CreationRequirement(5, 632, 2, true));
            canvaslesswagon.addRequirement(new CreationRequirement(6, 486, 2, true));
            Initiator.logInfo("Added creation entry for canvasless with templateID " + Initiator.wagonerItemID);
        }
    }
}