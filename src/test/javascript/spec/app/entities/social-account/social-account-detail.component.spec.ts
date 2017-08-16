/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { HalospainleagueTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SocialAccountDetailComponent } from '../../../../../../main/webapp/app/entities/social-account/social-account-detail.component';
import { SocialAccountService } from '../../../../../../main/webapp/app/entities/social-account/social-account.service';
import { SocialAccount } from '../../../../../../main/webapp/app/entities/social-account/social-account.model';

describe('Component Tests', () => {

    describe('SocialAccount Management Detail Component', () => {
        let comp: SocialAccountDetailComponent;
        let fixture: ComponentFixture<SocialAccountDetailComponent>;
        let service: SocialAccountService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HalospainleagueTestModule],
                declarations: [SocialAccountDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SocialAccountService,
                    JhiEventManager
                ]
            }).overrideTemplate(SocialAccountDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SocialAccountDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SocialAccountService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SocialAccount(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.socialAccount).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
