import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { ConversaoUnidadeUpdateComponent } from 'app/entities/conversao-unidade/conversao-unidade-update.component';
import { ConversaoUnidadeService } from 'app/entities/conversao-unidade/conversao-unidade.service';
import { ConversaoUnidade } from 'app/shared/model/conversao-unidade.model';

describe('Component Tests', () => {
  describe('ConversaoUnidade Management Update Component', () => {
    let comp: ConversaoUnidadeUpdateComponent;
    let fixture: ComponentFixture<ConversaoUnidadeUpdateComponent>;
    let service: ConversaoUnidadeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [ConversaoUnidadeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ConversaoUnidadeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConversaoUnidadeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConversaoUnidadeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ConversaoUnidade(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ConversaoUnidade();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
