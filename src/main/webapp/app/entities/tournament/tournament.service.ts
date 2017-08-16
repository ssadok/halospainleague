import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Tournament } from './tournament.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TournamentService {

    private resourceUrl = 'api/tournaments';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(tournament: Tournament): Observable<Tournament> {
        const copy = this.convert(tournament);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(tournament: Tournament): Observable<Tournament> {
        const copy = this.convert(tournament);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Tournament> {
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
        entity.registrationStarts = this.dateUtils
            .convertDateTimeFromServer(entity.registrationStarts);
        entity.registrationEnds = this.dateUtils
            .convertDateTimeFromServer(entity.registrationEnds);
        entity.tournamentBegins = this.dateUtils
            .convertDateTimeFromServer(entity.tournamentBegins);
    }

    private convert(tournament: Tournament): Tournament {
        const copy: Tournament = Object.assign({}, tournament);

        copy.registrationStarts = this.dateUtils.toDate(tournament.registrationStarts);

        copy.registrationEnds = this.dateUtils.toDate(tournament.registrationEnds);

        copy.tournamentBegins = this.dateUtils.toDate(tournament.tournamentBegins);
        return copy;
    }
}
