import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { ProfileConfiguration } from './profile-configuration.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ProfileConfigurationService {

    private resourceUrl = 'api/profile-configurations';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(profileConfiguration: ProfileConfiguration): Observable<ProfileConfiguration> {
        const copy = this.convert(profileConfiguration);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(profileConfiguration: ProfileConfiguration): Observable<ProfileConfiguration> {
        const copy = this.convert(profileConfiguration);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<ProfileConfiguration> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.lastLogin = this.dateUtils
            .convertDateTimeFromServer(entity.lastLogin);
    }

    private convert(profileConfiguration: ProfileConfiguration): ProfileConfiguration {
        const copy: ProfileConfiguration = Object.assign({}, profileConfiguration);

        copy.lastLogin = this.dateUtils.toDate(profileConfiguration.lastLogin);
        return copy;
    }
}
