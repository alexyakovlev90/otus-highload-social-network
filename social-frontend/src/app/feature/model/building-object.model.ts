import {IBuildingMaterial} from "./building-material.model";
import {EntityItem} from "../../shared/service/abstract.item.service";

export interface IBuildingObject extends EntityItem {
    id?: number;
    name?: string;
    location?: string;
    buildingMaterials?: IBuildingMaterial[];
    routeId?: number;
}

export class BuildingObject implements IBuildingObject {
    constructor(
        public id?: number,
        public name?: string,
        public location?: string,
        public buildingMaterials?: IBuildingMaterial[],
        public routeId?: number
    ) {}
}
