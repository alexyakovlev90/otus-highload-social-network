export interface IBuildingMaterial {
    id?: number;
    name?: string;
    quantity?: number;
    buildingObjectId?: number;
    vehicleId?: number;
    routeId?: number;
    factoryId?: number;
}

export class BuildingMaterial implements IBuildingMaterial {
    constructor(
        public id?: number,
        public name?: string,
        public quantity?: number,
        public buildingObjectId?: number,
        public vehicleId?: number,
        public routeId?: number,
        public factoryId?: number
    ) {}
}
