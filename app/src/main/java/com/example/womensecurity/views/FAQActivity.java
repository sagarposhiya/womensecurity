package com.example.womensecurity.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.MediaStore;

import com.example.womensecurity.Adapter.FAQAdapter;
import com.example.womensecurity.R;
import com.example.womensecurity.models.Genre;

import java.util.ArrayList;
import java.util.List;

public class FAQActivity extends AppCompatActivity {

    List<Genre> genres = new ArrayList<>();
    List<String> questions = List.of("How do you keep a girl safe?","How do I protect myself as a girl at night?","Which is the safest city in India for females?",
            "Why is women's safety important?", "Is India safe for girls?","How do I protect myself at night?","How can a woman walk alone?","Which is the most dangerous state in India?",
            "What is self safety?","Is Delhi safe for girls?","How do you stay safe when alone?","Why is night time dangerous?");

    List<String> answers = List.of("Safety tips for Women\n" +
            "Be aware of your surroundings. ...\n" +
            "Please trust and make good use of your 'gut feel' or 'intuition' or 'sixth sense' in each & every place and situation. ...\n" +
            "Don't take eve teasing lightly. ...\n" +
            "As much as possible avoid late night travel using public transport.",

            "In these situations, follow these guidelines for safety:\n" +
                    "Walk with purpose and don't stop.\n" +
                    "Invest in a way you could call for help if you needed to.\n" +
                    "Carry something in your hand.\n" +
                    "Stay away from alleyways and dark corners.\n" +
                    "Trust your gut.",

            "Kolkata\n" +
                    "Kolkata has been announced as the safest city in the country for women, as per the recently released data of the National Crime Record Board (NCRB).",

            "It helps women be calmer, understanding, flexible, gain body and mind control, be more responsive than reactive, more observant, and achieve cognitive awareness.",

            "Being a woman is not easy and that too when you live in India. Because in India at every 20 minutes a girl is raped. And if we talked about the current scenario, India is not safe for women not even in this 21st century. This country has been ranked as the world most dangerous place for women.",

            "How to Protect Yourself While Walking at Night\n" +
                    "Avoid walking or running alone at night. ...\n" +
                    "Don't use headphones while walking, driving or jogging.\n" +
                    "Always walk in well-lighted areas.\n" +
                    "Avoid the use of short cuts.\n" +
                    "After dark, keep away from large bushes or doorways where someone could be lurking.\n" +
                    "Always stay near the curb.",

            "Top 7 Tips to Stay Safe While Walking Alone\n" +
                    "Do Not Take/ Accept Lift from Anyone. Do not take lift from anyone unknown even though they look harmless. ...\n" +
                    "Don't Wear Headphones or Earphones. ...\n" +
                    "Keep Your Keys Handy. ...\n" +
                    "Walk With Confidence. ...\n" +
                    "Use Well- Lit & Busy Streets. ...\n" +
                    "Use of Woman Safety Devices.",

            "Top 10 Dangerous States in India:\n" +
                    "Uttar Pradesh: The crime rate per capita of UP is 7.4. ...\n" +
                    "Arunachal Pradesh: The crime rate and other factors have made the state responsible to land on the 2nd position in danger. ...\n" +
                    "Jharkhand: Jharkhand is another state not to be missed in danger terms. ...\n" +
                    "Meghalaya: ...\n" +
                    "Delhi: ...\n" +
                    "Assam: ...\n" +
                    "Chhattisgarh: ...\n" +
                    "Haryana:",

            "Self-defense (self-defence in some varieties of English) is a countermeasure that involves defending the health and well-being of oneself from harm. The use of the right of self-defense as a legal justification for the use of force in times of danger is available in many jurisdictions.",


            "Women Safety In Delhi – When it comes to women safety, Delhi is already considered the most dangerous city. Yes, the capital of India is not safe even in the daytime. ... Women Safety In Delhi, Girls don't feel safe on the roads and often avoid to go to the remote places withing the Delhi.",

            "Make sure you follow these tips to stay safer.\n" +
                    "Keep your keys ready. ...\n" +
                    "Walk with confidence. ...\n" +
                    "Trust your gut. ...\n" +
                    "Carry a noisy friend. ...\n" +
                    "Know what to do in the worst-case scenario. ...\n" +
                    "Avoid struggling with lots of bags. ...\n" +
                    "Avoid hiding spots. ...\n" +
                    "Keep walkways clear.",

            "Night is often associated with danger and evil, because of the psychological connection of night's all-encompassing darkness to the fear of the unknown and darkness's obstruction of a major sensory system (the sense of sight). Nighttime is naturally associated with vulnerability and danger for human physical survival."
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_a_q);
        genres = getGenres();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        //instantiate your adapter with the list of genres
        FAQAdapter adapter = new FAQAdapter(genres);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private List<Genre> getGenres() {
        List<Genre> genres = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            Genre genre = new Genre(questions.get(i),answers.get(i));
            genres.add(genre);
        }

        return genres;
    }
}