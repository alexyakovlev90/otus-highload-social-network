import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AbstractItemService} from "../../shared/service/abstract.item.service";
import {IBuildingObject} from "../model/building-object.model";

@Injectable(
  {providedIn: 'root'}
)
export class BuildingObjectService extends AbstractItemService<IBuildingObject,IBuildingObject> {

  constructor(protected httpClient: HttpClient) {
    super(httpClient);
  }

  protected apiUrl(): string {
    return "/api/building-objects/";
  }



}
